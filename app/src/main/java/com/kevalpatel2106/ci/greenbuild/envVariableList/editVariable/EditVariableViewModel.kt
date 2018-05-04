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

package com.kevalpatel2106.ci.greenbuild.envVariableList.editVariable


import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.EnvVars
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


internal class EditVariableViewModel @Inject constructor(
        private var application: BaseApplication,
        private val serverInterface: ServerInterface,
        compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {

    internal val isEditPublicVariableSupported = compatibilityCheck.isPublicEnvironmentVariableEditSupported()

    internal val isEditPrivateVariableSupported = compatibilityCheck.isPrivateEnvironmentVariableEditSupported()

    internal val errorEditingVar = SingleLiveEvent<String>()

    internal val isEmptyName = SingleLiveEvent<String>()

    internal val isEmptyValue = SingleLiveEvent<String>()

    internal var isSavingVariable = MutableLiveData<Boolean>()

    internal var variableEditSuccess = MutableLiveData<EnvVars>()


    init {
        isSavingVariable.value = false
        if (!isEditPublicVariableSupported && !isEditPrivateVariableSupported) {
            throw IllegalStateException("Editing variable is not supported for this CI platform.")
        }
    }

    internal fun editVariable(repoId: String,
                              envVarId: String,
                              newName: String,
                              newValue: String,
                              isPublic: Boolean) {

        if (newName.isBlank()) {
            isEmptyName.value = application.getString(R.string.error_edit_variable_empty_name)
            return
        }

        if (newValue.isBlank()) {
            isEmptyValue.value = application.getString(R.string.error_edit_variable_empty_value)
            return
        }

        val d = serverInterface
                .editEnvVariable(repoId, envVarId, newName, newValue, isPublic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isSavingVariable.value = true }
                .doOnTerminate { isSavingVariable.value = false }
                .subscribe({
                    variableEditSuccess.value = it
                }, {
                    errorEditingVar.value = it.message
                })

        addDisposable(d)
    }

}
