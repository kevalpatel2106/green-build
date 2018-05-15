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

package com.kevalpatel2106.ci.greenbuild.base.application

import android.app.Application
import android.content.Context
import com.kevalpatel2106.ci.greenbuild.base.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.GBRxSchedulers
import com.kevalpatel2106.ci.greenbuild.base.db.GBDatabase
import com.kevalpatel2106.greenbuild.utils.SharedPrefsProvider
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Kevalpatel2106 on 17-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: BaseApplication)

    fun getContext(): Context

    fun getApplication(): Application

    fun getBaseApplication(): BaseApplication

    fun getAccountManager(): AccountsManager

    fun getSharedPrefsManager(): SharedPrefsProvider

    fun getDatabase(): GBDatabase

    fun getRxSchedulers(): GBRxSchedulers
}
