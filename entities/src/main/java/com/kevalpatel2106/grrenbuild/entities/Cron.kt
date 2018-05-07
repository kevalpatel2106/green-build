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

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Kevalpatel2106 on 24-Apr-18.
 * Kotlin bin to information fot the scheduled cron for [repo].
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Entity(tableName = Cron.CRON_TABLE_NAME)
data class Cron(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = CRON_LOCAL_ID)
        var localId: Long,

        @ColumnInfo(name = CRON_ID)
        var id: String,

        @ColumnInfo(name = CRON_NEXT_RUN)
        var nextRun: Long,

        @ColumnInfo(name = CRON_LAST_RUN)
        var lastRun: Long,

        @Embedded(prefix = "crons_")
        var branch: Branch,

        @ColumnInfo(name = CRON_CREATE_TIME)
        var createdAt: Long,

        @ColumnInfo(name = CRON_RUN_IF_RECENTLY_BUILT)
        var isRunIfRecentlyBuilt: Boolean,

        @ColumnInfo(name = CRON_CAN_I_DELETE)
        var canIDelete: Boolean,

        @ColumnInfo(name = CRON_CAN_I_START)
        var canIStartCron: Boolean,

        @ColumnInfo(name = CRON_REPO_ID)
        var repoId: String
) : Parcelable {

    var isDeleting = false
    var isStartingCron = false

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readParcelable(Branch::class.java.classLoader),
            parcel.readLong(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
        isDeleting = parcel.readByte() != 0.toByte()
        isStartingCron = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(localId)
        parcel.writeString(id)
        parcel.writeLong(nextRun)
        parcel.writeLong(lastRun)
        parcel.writeParcelable(branch, flags)
        parcel.writeLong(createdAt)
        parcel.writeByte(if (isRunIfRecentlyBuilt) 1 else 0)
        parcel.writeByte(if (canIDelete) 1 else 0)
        parcel.writeByte(if (canIStartCron) 1 else 0)
        parcel.writeString(repoId)
        parcel.writeByte(if (isDeleting) 1 else 0)
        parcel.writeByte(if (isStartingCron) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cron> {
        const val CRON_TABLE_NAME = "crons"
        const val CRON_LOCAL_ID = "cron_local_id"
        const val CRON_ID = "cron_id"
        const val CRON_NEXT_RUN = "cron_next_run"
        const val CRON_LAST_RUN = "cron_last_run"
        const val CRON_CREATE_TIME = "cron_create_time"
        const val CRON_RUN_IF_RECENTLY_BUILT = "cron_run_if_recently_built"
        const val CRON_CAN_I_DELETE = "cron_can_i_delete"
        const val CRON_CAN_I_START = "cron_can_i_start"
        const val CRON_REPO_ID = "cron_repo_id"

        override fun createFromParcel(parcel: Parcel): Cron {
            return Cron(parcel)
        }

        override fun newArray(size: Int): Array<Cron?> {
            return arrayOfNulls(size)
        }
    }

}
