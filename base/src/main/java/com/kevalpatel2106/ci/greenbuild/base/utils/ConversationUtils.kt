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

package com.kevalpatel2106.ci.greenbuild.base.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Kevalpatel2106 on 23-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class ConversationUtils {

    companion object {
        private val dateFormatter = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())

        fun rfc3339ToMills(dateInString: String): Long {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault())
            cal.time = sdf.parse(dateInString
                    .replace("Z", "")
                    .replace("T", "-")
            )

            return cal.timeInMillis
        }

        fun humanReadableByteCount(bytes: Long): String {
            val unit = 1024

            if (bytes < unit) return bytes.toString() + " B"

            val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
            return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), ("KMGTPE")[exp - 1])
        }

        fun millsToDateFormat(mills: Long): String {
            return dateFormatter.format(Date(mills))
        }

        fun convertToHumanReadableDuration(timeSeconds: Int): String {
            var duration = ""

            with(timeSeconds / 3600) {
                if (this != 0) {
                    duration = if (this > 1)
                        duration.plus("$this hours ")
                    else
                        duration.plus("$this hour ")
                }
            }

            with((timeSeconds / 60) - (timeSeconds / 3600)) {
                if (this != 0) {
                    duration = duration.plus("$this min ")
                }
            }

            with(timeSeconds % 60) {
                if (this != 0) {
                    duration = duration.plus("$this sec")
                }
            }

            return duration
        }
    }

}
