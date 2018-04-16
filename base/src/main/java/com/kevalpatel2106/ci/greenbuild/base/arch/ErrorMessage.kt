/*
 *  Copyright 2018 Keval Patel.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.kevalpatel2106.ci.greenbuild.base.arch

import android.content.Context
import android.support.annotation.StringRes
import android.support.annotation.VisibleForTesting

/**
 * Created by Kevalpatel2106 on 30-Nov-17.
 * This POKO holds the error information such as error message, error animations and retry button name.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class ErrorMessage {

    /**
     * Error message to display. This is sting message.
     */
    @VisibleForTesting
    internal val errorMessage: String?

    /**
     * Error message string resource. This will hold the int id of the string resource. It doesn't
     * convert [errorMessageRes] in to [errorMessage] to avoid [Context] dependency. You can get
     * error message in string format by using [getMessage].
     */
    @VisibleForTesting
    @StringRes
    val errorMessageRes: Int

    /***
     * Animation json file for [Lottie](https://github.com/airbnb/lottie-android) animations.
     */
    var errorImage: String? = null

    /**
     * String resource for the text to display on the retry button. Default value is 0.
     */
    @StringRes
    private var errorBtnText: Int = 0

    /**
     * A function to perform specific task on "Retry" button click.
     */
    private var onErrorClick: (() -> Unit)? = null

    /**
     * Set the [errorMessageRes] as error text.. This will reset [errorMessage] to null. To set the
     * error button see [setErrorBtn]. To apply the error image check [errorImage].
     */
    constructor(@StringRes errorRes: Int) {
        errorMessage = null
        this.errorMessageRes = errorRes
    }

    /**
     * Set the [errorMessage] as error text. This will reset [errorMessageRes] to 0. To set the
     * error button see [setErrorBtn]. To apply the error image check [errorImage].
     */
    constructor(errorMessage: String?) {
        this.errorMessage = errorMessage
        this.errorMessageRes = 0
    }

    /**
     * Set the "Error" button with the [errorBtnText] and [onErrorClick] click event.
     *
     * @see errorBtnText
     * @see onErrorClick
     */
    fun setErrorBtn(@StringRes errorBtnText: Int, onErrorClick: () -> Unit) {
        this.errorBtnText = errorBtnText
        this.onErrorClick = onErrorClick
    }

    /**
     * Get the error message. Consumer can use this method to get the error message to display without
     * worrying about [errorMessage] and [errorMessageRes] values.
     *
     * @return [errorMessage] if the [errorMessage] is not null, or else string from the [errorMessageRes]
     * if the [context] is not null. If the [context] and [errorMessage ]is null, than iot will return
     * null.
     *
     * @see errorMessage
     * @see errorMessageRes
     */
    fun getMessage(context: Context?): String? {
        return errorMessage ?: context?.getString(errorMessageRes)
    }

    /**
     * Get the string resource for the error button text.
     *
     * @see errorBtnText
     */
    @StringRes
    fun getErrorBtnText() = errorBtnText

    /**
     * Get the function to perform specific task on "Retry" button click.
     *
     * @see onErrorClick
     */
    fun getOnErrorClick() = onErrorClick
}
