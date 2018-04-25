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

package com.kevalpatel2106.greenbuild.travisInterface.authentication

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Patterns
import com.kevalpatel2106.ci.greenbuild.base.account.Account
import com.kevalpatel2106.ci.greenbuild.base.account.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.arch.SingleLiveEvent
import com.kevalpatel2106.greenbuild.travisInterface.R
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
        private val application: BaseApplication,
        private val accountManager: AccountsManager
) : BaseViewModel() {

    internal val isValidationInProgress = MutableLiveData<Boolean>()

    internal val authenticationError = SingleLiveEvent<String>()

    internal val serverDomainValidationError = SingleLiveEvent<String>()

    internal val accessTokenValidationError = SingleLiveEvent<String>()

    internal val authenticatedAccount = MutableLiveData<Account>()

    init {
        isValidationInProgress.value = false
    }

    fun prepareApiUrl(serverUrl: String): String {
        if (serverUrl.contains(application.getString(R.string.schema_https))) {
            return serverUrl.replace(application.getString(R.string.schema_https),
                    "${application.getString(R.string.schema_https)}travis.")
                    .plus("/api/")
        }
        return ""
    }

    fun validateAuthToken(accessToken: String, apiUrl: String) {

        if (accessToken.isBlank()) {
            accessTokenValidationError.value = application.getString(R.string.error_invalid_travis_token)
            return
        }

        if (apiUrl.isBlank()
                || apiUrl == application.getString(R.string.schema_https)
                || !Patterns.WEB_URL.matcher(apiUrl).matches()) {
            serverDomainValidationError.value = application.getString(R.string.error_invalid_travis_domain)
            return
        }

        //We won't be injecting the server interface by dagger as there is no such token.
        with(TravisServerInterface.get(application = application, baseUrl = apiUrl, accessToken = accessToken)) {

            if (this == null) {
                //Invalid base URL format.
                //This should never happen!!!
                throw IllegalStateException("The url ($apiUrl) is not in travis CI api format. " +
                        "See https://developer.travis-ci.org/gettingstarted.")
            } else {
                this.getMyAccount()
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
                            authenticationError.value = it.message
                        })
            }
        }
    }
}
