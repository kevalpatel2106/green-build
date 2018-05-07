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

package com.kevalpatel2106.ci.greenbuild.base.db

import android.arch.persistence.room.TypeConverter
import com.kevalpatel2106.grrenbuild.entities.BuildState

/**
 * Created by Kevalpatel2106 on 07-May-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object BuildStateConverter {

    @TypeConverter
    fun toBuildState(value: Int): BuildState? {
        return when (value) {
            0 -> BuildState.PASSED
            1 -> BuildState.RUNNING
            2 -> BuildState.FAILED
            3 -> BuildState.CANCELED
            4 -> BuildState.ERRORED
            5 -> BuildState.BOOTING
            else -> BuildState.UNKNOWN
        }
    }

    @TypeConverter
    fun toInt(value: BuildState): Int {
        return when (value) {
            BuildState.PASSED -> 0
            BuildState.RUNNING -> 1
            BuildState.FAILED -> 2
            BuildState.CANCELED -> 3
            BuildState.ERRORED -> 4
            BuildState.BOOTING -> 5
            BuildState.UNKNOWN -> 6
        }
    }
}
