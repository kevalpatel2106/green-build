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

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.Fragment

/**
 * Created by Kevalpatel2106 on 29-Jan-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
fun AlertDialogHelper.defaultDialogButton() {
    positiveButton(android.R.string.ok)
    negativeButton(android.R.string.cancel)
    cancelable = false
}

fun Context.alert(title: CharSequence? = null,
                         message: CharSequence? = null,
                         func: AlertDialogHelper.() -> Unit = { defaultDialogButton() }): AlertDialog {
    val alertDialog =  AlertDialogHelper(this, title, message).apply {
        func()
    }.create()
    alertDialog.show()
    return alertDialog
}

fun Context.alert(titleResource: Int = 0,
                         messageResource: Int = 0,
                         func: AlertDialogHelper.() -> Unit = { defaultDialogButton() }): AlertDialog {
    val title = if (titleResource == 0) null else getString(titleResource)
    val message = if (messageResource == 0) null else getString(messageResource)
    val alertDialog = AlertDialogHelper(this, title, message).apply {
        func()
    }.create()
    alertDialog.show()
    return alertDialog
}

fun Fragment.alert(title: CharSequence? = null,
                          message: CharSequence? = null,
                          func: AlertDialogHelper.() -> Unit = { defaultDialogButton() }): AlertDialog {
    val alertDialog =  AlertDialogHelper(context!!, title, message).apply {
        func()
    }.create()
    alertDialog.show()
    return alertDialog
}

fun Fragment.alert(titleResource: Int = 0,
                          messageResource: Int = 0,
                          func: AlertDialogHelper.() -> Unit = { defaultDialogButton() }): AlertDialog {
    val title = if (titleResource == 0) null else getString(titleResource)
    val message = if (messageResource == 0) null else getString(messageResource)
    val alertDialog =  AlertDialogHelper(context!!, title, message).apply {
        func()
    }.create()
    alertDialog.show()
    return alertDialog
}

@SuppressLint("InflateParams")
class AlertDialogHelper(context: Context,
                        title: CharSequence? = null,
                        message: CharSequence? = null) {

    private val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)

    var cancelable = true

    private lateinit var dialog: AlertDialog

    fun positiveButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        builder.setPositiveButton(textResource) { _, _ -> func?.invoke() }
    }

    fun positiveButton(text: CharSequence, func: (() -> Unit)? = null) {
        builder.setPositiveButton(text) { _, _ -> func?.invoke() }
    }

    fun negativeButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        builder.setNegativeButton(textResource) { _, _ -> func?.invoke() }
    }

    fun negativeButton(text: CharSequence, func: (() -> Unit)? = null) {
        builder.setNegativeButton(text) { _, _ -> func?.invoke() }
    }

    fun neutralButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        builder.setNeutralButton(textResource) { _, _ -> func?.invoke() }
    }

    fun neutralButton(text: CharSequence, func: (() -> Unit)? = null) {
        builder.setNeutralButton(text) { _, _ -> func?.invoke() }
    }

    fun create(): AlertDialog {
        dialog = builder.setCancelable(cancelable).create()
        return dialog
    }
}
