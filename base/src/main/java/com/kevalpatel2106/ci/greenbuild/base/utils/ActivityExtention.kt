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

package com.kevalpatel2106.ci.greenbuild.base.utils

import android.annotation.SuppressLint
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.base.R

/**
 * Created by Keval on 17/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
/**
 * Display the snack bar.
 */
fun AppCompatActivity.showSnack(message: String,
                                actionName: String? = null,
                                actionListener: View.OnClickListener? = null,
                                duration: Int = Snackbar.LENGTH_SHORT): Snackbar {

    val snackbar = Snackbar.make((findViewById<ViewGroup>(android.R.id.content)).getChildAt(0),
            message, duration)

    actionName?.let {
        snackbar.setAction(actionName, actionListener)
        snackbar.setActionTextColor(getColorCompat(R.color.colorAccent))
    }

    snackbar.show()

    return snackbar
}

fun AppCompatActivity.showSnack(message: String,
                                actionName: Int,
                                actionListener: View.OnClickListener? = null,
                                duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return showSnack(message, getString(actionName), actionListener, duration)
}

@SuppressLint("ResourceType")
        /**
         * Display the snack bar.
         */
fun AppCompatActivity.showSnack(@StringRes message: Int,
                                @StringRes actionName: Int = 0,
                                actionListener: View.OnClickListener? = null,
                                duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snackbar = Snackbar.make((findViewById<ViewGroup>(android.R.id.content)).getChildAt(0),
            message, duration)
    if (actionName > 0) {
        snackbar.setAction(actionName, actionListener)
        snackbar.setActionTextColor(getColorCompat(R.color.colorAccent))
    }

    snackbar.show()
    return snackbar
}