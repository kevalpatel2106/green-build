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
import com.kevalpatel2106.grrenbuild.entities.TriggerType

/**
 * Created by Kevalpatel2106 on 07-May-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object TriggerTypeConverter {

    @TypeConverter
    fun toBuildState(value: Int): TriggerType? {
        return when (value) {
            0 -> TriggerType.PUSH
            1 -> TriggerType.PULL_REQUEST
            2 -> TriggerType.CRON
            3 -> TriggerType.API
            else -> TriggerType.PUSH
        }
    }

    @TypeConverter
    fun toInt(value: TriggerType): Int {
        return when (value) {
            TriggerType.PUSH -> 0
            TriggerType.PULL_REQUEST -> 1
            TriggerType.CRON -> 2
            TriggerType.API -> 3
        }
    }
}
