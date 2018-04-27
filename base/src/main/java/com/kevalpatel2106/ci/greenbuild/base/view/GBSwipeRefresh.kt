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
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import com.kevalpatel2106.ci.greenbuild.base.R

/**
 * Created by Kevalpatel2106 on 26-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class GBSwipeRefresh @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    override fun setRefreshing(refreshing: Boolean) {
        super.setRefreshing(refreshing)

        if (refreshing) {
            setColorSchemeResources(
                    R.color.swipe_color_0,
                    R.color.swipe_color_1,
                    R.color.swipe_color_2,
                    R.color.swipe_color_3
            )
        }
    }

}
