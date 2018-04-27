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
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Keval on 31-May-17.
 * This class is to extend the functionality of [ViewPager]. Use this class instead
 * of [ViewPager] through out the application.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

class GBViewPager @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
): ViewPager(context, attrs)  {

    private var mIsSwipeGestureEnable = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return this.mIsSwipeGestureEnable && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return this.mIsSwipeGestureEnable && super.onInterceptTouchEvent(event)
    }

    /**
     * @param enabled True to enable the swipe gesture for the view pager to change the currently
     * *                displaying page. By default this gesture is enabled.
     */
    fun setSwipeGestureEnable(enabled: Boolean) {
        this.mIsSwipeGestureEnable = enabled
    }
}
