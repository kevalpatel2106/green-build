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

package com.kevalpatel2106.ci.greenbuild.base.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * Created by Keval Patel on 04/03/17.
 * This base class is to extend the functionality of [AppCompatTextView]. Use this class instead
 * of [android.widget.TextView] through out the application.

 * @author 'https://github.com/kevalpatel2106'
 */

class BaseTextView : AppCompatTextView {
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

    }

    /**
     * @return Trimmed text.
     */
    fun getTrimmedText() = text.toString().trim { it <= ' ' }
}
