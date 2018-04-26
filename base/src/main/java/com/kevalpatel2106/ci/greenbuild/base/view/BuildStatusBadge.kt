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
import android.util.AttributeSet
import android.widget.LinearLayout
import com.kevalpatel2106.ci.greenbuild.base.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.BuildState
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.getBuildStateColorRes
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.getBuildStateName
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorStateListCompat

/**
 * Created by Keval on 26/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class BuildStatusBadge @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleTv: BaseTextView
    private val statusTv: BaseTextView

    var title: String = context.getString(R.string.build_status_view_default_title)
        set(value) {
            titleTv.text = value
            field = value
        }


    var buildStatus: BuildState = BuildState.UNKNOWN
        set(value) {
            field = value
            statusTv.text = buildStatus.getBuildStateName(context)
            statusTv.backgroundTintList = context.getColorStateListCompat(buildStatus
                    .getBuildStateColorRes(context))
        }

    init {
        inflate(context, R.layout.layout_build_title_badge, this@BuildStatusBadge)

        titleTv = findViewById(R.id.build_status_title_tv)
        statusTv = findViewById(R.id.build_status_tv)
    }


}