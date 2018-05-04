/*
 * Copyright 2018 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevalpatel2106.ci.greenbuild.cronList

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Cron
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.SingleLiveEvent
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.recall
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class CronListViewModel @Inject constructor(
        private val application: BaseApplication,
        private val serverInterface: ServerInterface,
        compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {
    internal val isDeleteCronSupported = compatibilityCheck.isDeleteCronJobsSupported()

    internal val isRunCronSupported = compatibilityCheck.isInitiateCronJobsSupported()

    internal val cronList = MutableLiveData<ArrayList<Cron>>()

    internal val errorLoadingList = SingleLiveEvent<String>()

    internal val errorDeleting = SingleLiveEvent<String>()

    internal val errorRunning = SingleLiveEvent<String>()

    internal val cronStartedSuccess = SingleLiveEvent<String>()

    internal var isLoadingList = MutableLiveData<Boolean>()

    internal var isLoadingFirstTime = MutableLiveData<Boolean>()

    internal var hasModeData = MutableLiveData<Boolean>()

    init {
        if (!compatibilityCheck.isCacheListListSupported())
            throw IllegalStateException("TravisCron listing by repository is not supported for this CI.")

        hasModeData.value = true
        isLoadingList.value = false
        isLoadingFirstTime.value = false

        cronList.value = ArrayList()
    }

    fun loadCronList(repoId: String, page: Int) {
        serverInterface.getCronsList(page, repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadingList.value = true

                    cronList.value?.let {
                        if (it.isEmpty()) isLoadingFirstTime.value = true
                    }
                }
                .doOnTerminate {
                    isLoadingList.value = false
                    isLoadingFirstTime.value = false
                }
                .map {
                    hasModeData.value = it.hasNext

                    if (page == 1) cronList.value!!.clear()

                    cronList.value!!.addAll(it.list)
                    return@map cronList
                }
                .subscribe({
                    cronList.recall()
                }, {
                    errorLoadingList.value = it.message
                })
    }

    fun deleteCron(cron: Cron, repoId: String) {
        if (!isDeleteCronSupported)
            throw IllegalStateException("Deleting cron is not supported for this CI platform.")

        serverInterface.deleteCron(repoId = repoId, cronId = cron.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    cron.isDeleting = true
                    cronList.recall()
                }
                .doOnTerminate {
                    cron.isDeleting = false
                    cronList.recall()
                }
                .subscribe({
                    cronList.value!!.remove(cron)
                }, {
                    errorDeleting.value = it.message
                })
    }

    fun runCron(cron: Cron, repoId: String) {
        if (!isRunCronSupported)
            throw IllegalStateException("Running cron manually is not supported for this CI platform.")

        serverInterface.startCronManually(repoId = repoId, cronId = cron.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    cron.isStartingCron = true
                    cronList.recall()
                }
                .doOnTerminate {
                    cron.isStartingCron = false
                    cronList.recall()
                }
                .subscribe({
                    cronStartedSuccess.value = application.getString(R.string.cron_started_success)
                }, {
                    errorRunning.value = it.message
                })
    }
}
