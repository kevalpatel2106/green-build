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

package com.kevalpatel2106.ci.greenbuild.envVariableList

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.arch.SingleLiveEvent
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.envVars.EnvVars
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
internal class EnvVarsListViewModel @Inject constructor(
        private val serverInterface: ServerInterface,
        compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {
    internal val isDeleteVariableSupported = compatibilityCheck.isEnvironmentVariableDeleteSupported()
    internal val isEditPublicVariableSupported = compatibilityCheck.isPublicEnvironmentVariableEditSupported()
    internal val isEditPrivateVariableSupported = compatibilityCheck.isPrivateEnvironmentVariableEditSupported()

    internal val envVarsList = MutableLiveData<ArrayList<EnvVars>>()

    internal val errorLoadingList = SingleLiveEvent<String>()

    internal var isLoadingList = MutableLiveData<Boolean>()

    internal var isLoadingFirstTime = MutableLiveData<Boolean>()

    internal var hasModeData = MutableLiveData<Boolean>()

    init {
        if (!compatibilityCheck.isEnvironmentVariableListSupported())
            throw IllegalStateException("Environment variables listing by repository is not supported for this CI.")

        hasModeData.value = true
        isLoadingList.value = false
        isLoadingFirstTime.value = false

        envVarsList.value = ArrayList()
    }

    fun loadEnvVarsList(repoId: String, page: Int) {
        serverInterface.getEnvironmentVariablesList(page, repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadingList.value = true

                    envVarsList.value?.let {
                        if (it.isEmpty()) isLoadingFirstTime.value = true
                    }
                }
                .doOnTerminate {
                    isLoadingList.value = false
                    isLoadingFirstTime.value = false
                }
                .subscribe({
                    hasModeData.value = it.hasNext

                    if (page == 1) envVarsList.value!!.clear()

                    envVarsList.value!!.addAll(it.list)
                    envVarsList.value = envVarsList.value
                }, {
                    errorLoadingList.value = it.message
                })
    }
}
