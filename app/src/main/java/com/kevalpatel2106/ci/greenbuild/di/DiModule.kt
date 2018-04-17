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

package com.kevalpatel2106.ci.greenbuild.di

import com.kevalpatel2106.ci.greenbuild.base.account.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.greenbuild.travisInterface.TravisServerInterface
import dagger.Module
import dagger.Provides

/**
 * Created by Kevalpatel2106 on 17-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
class DiModule {

    @Provides
    fun provideServerInterface(accountsManager: AccountsManager): ServerInterface {
        val account = accountsManager.getCurrentAccount()
        return when {
            account.serverUrl == ServerInterface.TRAVIS_CI_ORG -> {
                TravisServerInterface(ServerInterface.TRAVIS_CI_ORG, account.accessToken)
            }
            account.serverUrl == ServerInterface.TRAVIS_CI_COM -> {
                TravisServerInterface(ServerInterface.TRAVIS_CI_COM, account.accessToken)
            }
            account.serverUrl.startsWith("https://travis.")
                    && account.serverUrl.endsWith("/api/") -> {
                TravisServerInterface(account.serverUrl, account.accessToken)
            }
            else -> {
                throw IllegalStateException("Invalid server url: ${account.serverUrl}")
            }
        }
    }
}
