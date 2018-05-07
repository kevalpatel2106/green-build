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

package com.kevalpatel2106.greenbuild.utils

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View


/**
 * Created by Keval on 19/11/17.
 * This is the extensions for the activity.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
/**
 * Display the snack bar.
 */
fun Fragment.showSnack(message: String,
                       actionName: String? = null,
                       actionListener: View.OnClickListener? = null,
                       duration: Int = Snackbar.LENGTH_SHORT) {

    activity?.let {
        if (activity is AppCompatActivity)
            (activity as AppCompatActivity).showSnack(message = message,
                    actionName = actionName,
                    actionListener = actionListener,
                    duration = duration)
    }
}

/**
 * Display the snack bar.
 */
fun Fragment.showSnack(@StringRes message: Int,
                       @StringRes actionName: Int = 0,
                       actionListener: View.OnClickListener? = null,
                       duration: Int = Snackbar.LENGTH_SHORT) {
    activity?.let {
        if (activity is AppCompatActivity)
            (activity as AppCompatActivity).showSnack(message = message,
                    actionName = actionName,
                    actionListener = actionListener,
                    duration = duration)
    }
}
