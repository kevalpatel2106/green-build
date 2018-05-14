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

package com.kevalpatel2106.greenbuild.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Kevalpatel2106 on 23-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class ConversationUtils {

    companion object {
        private const val ONE_SECOND_MILLS = 1000L
        private const val ONE_MINUTE_MILLS = ONE_SECOND_MILLS * 60
        private const val ONE_HOUR_MILLS = ONE_MINUTE_MILLS * 60
        private const val ONE_DAY_MILLS = ONE_HOUR_MILLS * 24
        private const val ONE_MONTH_MILLS = ONE_HOUR_MILLS * 31

        private val timelineOffset = TimeZone.getDefault().rawOffset

        private val dateFormatter = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())

        fun rfc3339ToMills(dateInString: String): Long {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault())
            cal.time = sdf.parse(dateInString
                    .replace("Z", "")
                    .replace("T", "-")
            )
            return cal.timeInMillis + timelineOffset
        }

        fun humanReadableByteCount(bytes: Long): String {
            val unit = 1024

            if (bytes < unit) return bytes.toString() + " B"

            val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
            return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), ("KMGTPE")[exp - 1])
        }

        fun getDate(timeMills: Long): String {
            val duration = System.currentTimeMillis() - timeMills

            return if (duration > ONE_MONTH_MILLS) {
                return dateFormatter.format(Date(timeMills))
            } else {
                convertToHumanReadableDuration(duration)
            }
        }

        /**
         * converts time (in milliseconds) to human-readable format "<w> days, <x> hours, <y> minutes
         * and (z) seconds" </y></x></w>
         */
        fun convertToHumanReadableDuration(durationMills: Long, accurate: Boolean = false): String {
            var duration = durationMills
            val buffer = StringBuffer()
            var temp: Long

            if (duration >= ONE_SECOND_MILLS) {
                //Calculate days
                temp = duration / ONE_DAY_MILLS
                if (temp > 0) {
                    duration -= temp * ONE_DAY_MILLS
                    buffer.append(temp)
                            .append(" day")
                            .append(if (temp > 1) "s " else " ")
                }

                //Calculate hours
                temp = duration / ONE_HOUR_MILLS
                if (temp > 0) {
                    duration -= temp * ONE_HOUR_MILLS
                    buffer.append(temp)
                            .append(" hour")
                            .append(if (temp > 1) "s " else " ")
                }

                if (accurate || !buffer.contains("day")) {

                    //Calculate minutes
                    temp = duration / ONE_MINUTE_MILLS
                    if (temp > 0) {
                        duration -= temp * ONE_MINUTE_MILLS
                        buffer.append(temp).append(" min ")
                    }
                }

                if (accurate || !buffer.contains("hours")) {

                    //Calculate seconds
                    temp = duration / ONE_SECOND_MILLS
                    if (temp > 0) {
                        buffer.append(temp).append(" sec")
                    }
                }
                return buffer.toString()
            } else {
                return "now"
            }
        }
    }
}
