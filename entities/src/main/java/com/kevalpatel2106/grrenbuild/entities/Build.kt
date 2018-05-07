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
 * Created by Kevalpatel2106 on 18-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Entity(tableName = Build.BUILD_TABLE_NAME)
data class Build(

        @PrimaryKey(autoGenerate = true)
        @Suppress("MemberVisibilityCanBePrivate")
        val localId: Long,

        @ColumnInfo(name = BUILD_ID)
        val id: Long,

        @ColumnInfo(name = BUILD_NUMBER)
        val number: String,

        @ColumnInfo(name = BUILD_DURATION)
        var duration: Long,

        @ColumnInfo(name = BUILD_CURRENT_STATE)
        var state: BuildState,

        @ColumnInfo(name = BUILD_PREVIOUS_STATE)
        var previousState: BuildState?,

        @ColumnInfo(name = BUILD_START_TIME)
        var startedAt: Long = 0,

        @ColumnInfo(name = BUILD_END_TIME)
        var finishedAt: Long = 0,

        @ColumnInfo(name = BUILD_TRIGGER_TYPE)
        val triggerType: TriggerType,

        @Embedded
        val commitAuthor: Author,

        @Embedded
        val commitBranch: Branch,

        @Embedded
        val commit: Commit,

        @ColumnInfo(name = BUILD_REPO_NAME)
        val repoName: String?,

        @ColumnInfo(name = BUILD_REPO_ID)
        val repoId: String,

        @ColumnInfo(name = BUILD_OWNER_NAME)
        val ownerName: String?
) : Parcelable {

    var isRestarting: Boolean = false

    var isAborting: Boolean = false

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            BuildState.valueOf(parcel.readString()),
            with(parcel.readString()) {
                return@with if (this == null) null else BuildState.valueOf(this)
            },
            parcel.readLong(),
            parcel.readLong(),
            TriggerType.valueOf(parcel.readString()),
            parcel.readParcelable(Author::class.java.classLoader),
            parcel.readParcelable(Branch::class.java.classLoader),
            parcel.readParcelable(Commit::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
        isRestarting = parcel.readByte() != 0.toByte()
        isAborting = parcel.readByte() != 0.toByte()
    }

    init {
        if (startedAt != 0L && state in setOf(BuildState.RUNNING, BuildState.BOOTING)) {
            duration = System.currentTimeMillis() - startedAt
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(localId)
        parcel.writeLong(id)
        parcel.writeString(number)
        parcel.writeLong(duration)
        parcel.writeString(state.name)
        parcel.writeString(previousState?.name)
        parcel.writeLong(startedAt)
        parcel.writeLong(finishedAt)
        parcel.writeString(triggerType.name)
        parcel.writeParcelable(commitAuthor, flags)
        parcel.writeParcelable(commitBranch, flags)
        parcel.writeParcelable(commit, flags)
        parcel.writeString(repoName)
        parcel.writeString(repoId)
        parcel.writeString(ownerName)
        parcel.writeByte(if (isRestarting) 1 else 0)
        parcel.writeByte(if (isAborting) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Build> {
        const val BUILD_TABLE_NAME = "builds"
        const val BUILD_ID = "build_id"
        const val BUILD_REPO_ID = "build_repo_id"
        const val BUILD_NUMBER = "build_number"
        const val BUILD_DURATION = "build_duration"
        const val BUILD_CURRENT_STATE = "build_current_state"
        const val BUILD_PREVIOUS_STATE = "build_previous_state"
        const val BUILD_START_TIME = "build_start_time"
        const val BUILD_END_TIME = "build_end_time"
        const val BUILD_TRIGGER_TYPE = "build_trigger_type"
        const val BUILD_REPO_NAME = "build_repo_name"
        const val BUILD_OWNER_NAME = "build_owner_name"

        override fun createFromParcel(parcel: Parcel): Build {
            return Build(parcel)
        }

        override fun newArray(size: Int): Array<Build?> {
            return arrayOfNulls(size)
        }
    }
}
