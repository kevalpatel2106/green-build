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

package com.kevalpatel2106.ci.greenbuild.base.view

import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.WindowManager
import com.kevalpatel2106.ci.greenbuild.base.R

/**
 * Created by Keval on 24/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
abstract class GBDialogFragment : DialogFragment() {

    abstract fun fullWidth(): Boolean

    abstract fun fullHeight(): Boolean

    /**
     * Make the dialog at least 90% of the screen width. The height will be wrap content. Also apply
     * [R.style.DialogAnimation] to the dialog.
     */
    override fun onStart() {
        super.onStart()
        val window = dialog.window ?: return

        //Display the dialog full width of the screen
        window.setLayout(
                if (fullWidth()) {
                    WindowManager.LayoutParams.MATCH_PARENT
                } else {
                    WindowManager.LayoutParams.WRAP_CONTENT
                },
                if (fullHeight()) {
                    WindowManager.LayoutParams.MATCH_PARENT
                } else {
                    WindowManager.LayoutParams.WRAP_CONTENT
                }
        )

        //Display the at the bottom of the screen
        val wlp = window.attributes
        wlp.gravity = Gravity.CENTER
        wlp.windowAnimations = R.style.DialogAnimation
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
    }

    /**
     * This code is to stop the dialog from being dismissed on rotation, due to a
     * [bug](https://code.google.com/p/android/issues/detail?id=17423) with the compatibility library.
     *
     * See: [POST](https://stackoverflow.com/a/15444485/4690731)
     */
    override fun onDestroyView() {
        val dialog = dialog
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }
}
