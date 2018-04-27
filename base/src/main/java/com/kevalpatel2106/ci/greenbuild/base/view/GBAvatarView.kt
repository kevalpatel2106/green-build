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

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.kevalpatel2106.ci.greenbuild.base.R
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorCompat
import com.mklimek.circleinitialsview.CircleInitialsView

/**
 * Created by Keval Patel on 04/03/17.
 * This base class is to extend the functionality of [AppCompatImageView]. Use this class instead
 * of [android.widget.ImageView] through out the application.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

class GBAvatarView(context: Context?, attrs: AttributeSet?) : CircleInitialsView(context, attrs) {

    override fun setText(text: String) {
        super.setText(text.toUpperCase())
        textColor = Color.WHITE
        backgroundColor = context.getColorCompat(when (text.first().toInt() % 8) {
            0 -> R.color.avatar_view_bg_0
            1 -> R.color.avatar_view_bg_1
            2 -> R.color.avatar_view_bg_2
            3 -> R.color.avatar_view_bg_3
            4 -> R.color.avatar_view_bg_4
            5 -> R.color.avatar_view_bg_5
            6 -> R.color.avatar_view_bg_6
            7 -> R.color.avatar_view_bg_7
            else -> throw IllegalStateException("This case should not happen!!!")
        })
    }

}
