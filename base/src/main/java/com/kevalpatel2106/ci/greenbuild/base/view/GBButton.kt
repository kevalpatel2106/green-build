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

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.MotionEvent
import com.kevalpatel2106.ci.greenbuild.base.view.progressButton.ProgressButton
import com.kevalpatel2106.greenbuild.utils.Utils


/**
 * Created by Keval Patel on 04/03/17.
 * This base class is to extend the functionality of [AppCompatButton]. Use this class instead
 * of [android.widget.Button] through out the application.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class GBButton : ProgressButton {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(@Suppress("UNUSED_PARAMETER") context: Context) {
        //set type face
        //setTypeface(ResourcesCompat.getFont(context, R.font.open_sans));

        setPaddingProgress(Utils.toPx(context, 8).toFloat())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //Action down
            if (event?.action == MotionEvent.ACTION_DOWN) {
                translationZ -= 20  //Fall
            }

            //Action up
            if (event?.action == MotionEvent.ACTION_UP) {
                translationZ += 20  //Rise
            }
        }
        return super.onTouchEvent(event)
    }

    fun displayLoader(display: Boolean) {
        isEnabled = !display
        if (display) {
            startAnimation()
        } else {
            revertAnimation()
        }
    }
}
