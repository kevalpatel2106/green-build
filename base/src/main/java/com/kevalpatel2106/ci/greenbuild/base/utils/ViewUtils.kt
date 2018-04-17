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

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Created by Keval on 08-Sep-17.
 * View utils for handling the view operations.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

object ViewUtils {

    @JvmStatic
    fun toPx(context: Context, dp: Int): Int = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics)
            .toInt()

    @JvmStatic
    fun showKeyboard(v: View, activity: Context) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(v, 0)
    }

    @JvmStatic
    fun showKeyboard(v: View) = showKeyboard(v, v.context)

    @JvmStatic
    fun hideKeyboard(view: View?) {
        if (view != null) {
            val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

