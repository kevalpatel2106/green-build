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

package com.kevalpatel2106.grrenbuild.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Kevalpatel2106 on 24-Apr-18.
 * Kotlin bin to information fot the scheduled cron for [repo].
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Cron(

        val id: String,

        val nextRun: Long,

        val lastRun: Long,

        val branchName: String,

        val createdAt: Long,

        val isRunIfRecentlyBuilt: Boolean = true,

        val canIDelete: Boolean,

        val canIStartCron: Boolean,

        val repo: Repo
) : Parcelable {

    var isDeleting = false
    var isStartingCron = false

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()) {
        isDeleting = parcel.readByte() != 0.toByte()
        isStartingCron = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(nextRun)
        parcel.writeLong(lastRun)
        parcel.writeString(branchName)
        parcel.writeLong(createdAt)
        parcel.writeByte(if (isRunIfRecentlyBuilt) 1 else 0)
        parcel.writeByte(if (canIDelete) 1 else 0)
        parcel.writeByte(if (canIStartCron) 1 else 0)
        parcel.writeByte(if (isDeleting) 1 else 0)
        parcel.writeByte(if (isStartingCron) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cron> {
        override fun createFromParcel(parcel: Parcel): Cron {
            return Cron(parcel)
        }

        override fun newArray(size: Int): Array<Cron?> {
            return arrayOfNulls(size)
        }
    }
}
