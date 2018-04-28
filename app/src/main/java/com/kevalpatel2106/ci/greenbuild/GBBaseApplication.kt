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

package com.kevalpatel2106.ci.greenbuild

import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.moduleCallback.TravisCallbacks
import timber.log.Timber

/**
 * Created by Kevalpatel2106 on 28-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
abstract class GBBaseApplication: BaseApplication(){

    override fun onCreate() {
        super.onCreate()

        //Initialize the travis callbacks
        TravisCallbacks()

        //Enable timber
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return (super.createStackElementTag(element) + ":" + element.methodName
                        + " ->L" + element.lineNumber)
            }
        })

        throw IllegalArgumentException("Testing crashalytics.")
    }

}
