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

package com.kevalpatel2106.ci.greenbuild.authentication

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.account.Account
import com.kevalpatel2106.ci.greenbuild.base.account.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.arch.SingleLiveEvent
import com.kevalpatel2106.greenbuild.travisInterface.TravisServerInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.Exceptions
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 17-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class TravisAuthenticationViewModel @Inject constructor(
        private val application: Application,
        private val accountManager: AccountsManager
) : BaseViewModel() {

    internal val isValidationInProgress = MutableLiveData<Boolean>()

    internal val invalidAuthTokenError = SingleLiveEvent<String>()

    internal val authenticatedAccount = MutableLiveData<Account>()

    init {
        isValidationInProgress.value = false
    }

    fun validateAuthToken(accessToken: String, serverUrl: String) {

        if (accessToken.isBlank()) {
            invalidAuthTokenError.value = application.getString(R.string.error_invalid_travis_token)
            return
        }

        //We won't be injecting the server interface by dagger as there is no such token.
        TravisServerInterface(baseUrl = serverUrl, accessToken = accessToken)
                .getMyAccount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    isValidationInProgress.value = true
                }
                .doOnTerminate {
                    isValidationInProgress.value = false
                }
                .map {
                    accountManager.getAccount(it.accountId)?.let {
                        throw Exceptions.propagate(Throwable(application
                                .getString(R.string.error_account_already_registered)))
                    }
                    return@map it
                }
                .map {
                    //Save the access token inside it.
                    it.accessToken = accessToken
                    return@map it
                }
                .subscribe({
                    accountManager.saveAccount(it)
                    accountManager.changeCurrentAccount(it.accountId)

                    authenticatedAccount.value = it
                }, {
                    invalidAuthTokenError.value = it.message
                })
    }
}
