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

package com.kevalpatel2106.greenbuild.travisInterface

import android.content.Context

/**
 * Created by Keval on 23/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
abstract class TravisModuleCallbacks {

    companion object {
        internal lateinit var instance: TravisModuleCallbacks

        fun init(travisModuleCallbacks: TravisModuleCallbacks) {
            this.instance = travisModuleCallbacks
        }
    }

    abstract fun openHome(context: Context)
}