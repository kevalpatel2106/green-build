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

/**
 * Created by Keval on 31/12/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class BaseApplication : Application() {
    private lateinit var appComponent: ApplicationComponent

    companion object {

        fun get(context: Context): BaseApplication {
            return context.applicationContext as BaseApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    fun getApplicationComponent(): ApplicationComponent {
        return appComponent
    }

    private fun createAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this@BaseApplication))
                .build()

        //Inject dagger
        appComponent.inject(this@BaseApplication)
    }
}
