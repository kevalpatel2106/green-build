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

package com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities

import android.content.Context
import android.support.annotation.ColorInt
import com.kevalpatel2106.ci.greenbuild.base.R
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorCompat

/**
 * Created by Kevalpatel2106 on 18-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
enum class BuildState {
    PASSED,
    RUNNING,
    FAILED,
    CANCELED,
    ERRORED,
    BOOTING
}

@ColorInt
fun BuildState.getBuildStateColor(context: Context): Int {
    return when (this) {
        BuildState.PASSED -> context.getColorCompat(R.color.build_passed)
        BuildState.RUNNING -> context.getColorCompat(R.color.build_running)
        BuildState.FAILED -> context.getColorCompat(R.color.build_failed)
        BuildState.CANCELED -> context.getColorCompat(R.color.build_canceled)
        BuildState.ERRORED -> context.getColorCompat(R.color.build_errored)
        BuildState.BOOTING -> context.getColorCompat(R.color.build_booting)
    }
}

fun BuildState.getBuildStateName(context: Context): String {
    return when (this) {
        BuildState.PASSED -> context.getString(R.string.build_state_passed)
        BuildState.RUNNING -> context.getString(R.string.build_state_running)
        BuildState.FAILED -> context.getString(R.string.build_state_failed)
        BuildState.CANCELED -> context.getString(R.string.build_state_canceled)
        BuildState.ERRORED -> context.getString(R.string.build_state_error)
        BuildState.BOOTING -> context.getString(R.string.build_state_booting)
    }
}
