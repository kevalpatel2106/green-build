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

package com.kevalpatel2106.ci.greenbuild.base


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by Kevalpatel2106 on 28-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object AnalyticsEvents {

    //------------------Authentication flow events------------------//
    const val EVENT_ADD_CI = FirebaseAnalytics.Event.LOGIN

    //------------------Bundle keys------------------//
    const val ACCOUNT_TYPE = "account_type"
    internal const val KEY_DEVICE_NAME = "device_name"
    internal const val KEY_DEVICE_MANUFACTURE = "device_manufacture"
    internal const val KEY_APP_VERSION = "app_version"
}

@SuppressLint("MissingPermission")
fun Context.logEvent(event: String,
                     bundle: Bundle? = null) {

    val param = bundle ?: Bundle()
    param.apply {
        putString(AnalyticsEvents.KEY_DEVICE_NAME, Build.DEVICE)
        putString(AnalyticsEvents.KEY_DEVICE_MANUFACTURE, Build.MANUFACTURER)
        putString(AnalyticsEvents.KEY_APP_VERSION, "${com.kevalpatel2106.greenbuild.utils.BuildConfig.VERSION_NAME}(${com.kevalpatel2106.greenbuild.utils.BuildConfig.VERSION_CODE})")
    }

    FirebaseAnalytics.getInstance(applicationContext).logEvent(event, param)
}
