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

package com.kevalpatel2106.ci.greenbuild.cronList.addCron

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.arch.SingleLiveEvent
import com.kevalpatel2106.ci.greenbuild.base.arch.recall
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Cron
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 24-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class AddCronViewModel @Inject constructor(
        private val application: BaseApplication,
        private val serverInterface: ServerInterface,
        compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {

    internal val isSchedulingCron = MutableLiveData<Boolean>()

    internal val isLoadingIntervalList = MutableLiveData<Boolean>()

    internal val scheduledCron = MutableLiveData<Cron>()

    internal val errorLoadingInterval = SingleLiveEvent<String>()

    internal val errorSchedulingCron = SingleLiveEvent<String>()

    internal val intervalList = MutableLiveData<ArrayList<String>>()

    internal val invalidBranch = MutableLiveData<String>()

    internal val invalidInterval = MutableLiveData<String>()

    init {
        if (!compatibilityCheck.isAddCronJobsSupported())
            throw IllegalStateException("This CI provider doesn't support for scheduling new cron.")

        isSchedulingCron.value = false
        isLoadingIntervalList.value = false
        intervalList.value = ArrayList()

        //Load the intervals
        loadCronIntervals()
    }

    private fun loadCronIntervals() {
        serverInterface.supportedCronIntervals()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { isLoadingIntervalList.value = false }
                .doOnSubscribe { isLoadingIntervalList.value = true }
                .subscribe({
                    intervalList.value!!.clear()
                    intervalList.value!!.addAll(it)
                    intervalList.recall()
                }, {
                    errorLoadingInterval.value = application.getString(R.string.add_cron_error_cannot_load_interval)
                })
    }

    internal fun scheduleCron(
            repoId: String,
            branchName: String,
            interval: String,
            dontRunIfRecentlyBuilt: Boolean = false
    ) {
        if (interval.isBlank() || !intervalList.value!!.contains(interval)){
            invalidInterval.value = application.getString(R.string.error_invalid_interval)
            return
        }

        if (branchName.isBlank()){
            invalidBranch.value = application.getString(R.string.error_invalid_branch)
            return
        }

        serverInterface
                .scheduleCron(
                        repoId = repoId,
                        branchName = branchName,
                        interval = interval,
                        dontRunIfRecentlyBuilt = dontRunIfRecentlyBuilt
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { isSchedulingCron.value = false }
                .doOnSubscribe { isSchedulingCron.value = true }
                .subscribe({
                    scheduledCron.value = it
                }, {
                    errorSchedulingCron.value = it.message
                })
    }

}
