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

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Ignore
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Keval on 06/05/18.
 * Kotlin bin to hold the basic information about the commit in the [Repo].
 *
 * @constructor Public constructor.
 * @param sha Unique SHA off the commit.
 * @param committedAt Commit time in milliseconds.
 * @param message Commit message. The commit message should never be null.
 * @param tagName Name of the tag if the commit is associate with any tag, otherwise it will be null.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Commit(

        @ColumnInfo(name = COMMIT_TIME)
        var committedAt: Long = 0,

        @ColumnInfo(name = COMMIT_MESSAGE)
        var message: String,

        @ColumnInfo(name = COMMIT_SHA)
        var sha: String,

        @ColumnInfo(name = COMMIT_TAG_NAME)
        var tagName: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun equals(other: Any?): Boolean {
        return other != null && other is Commit && other.sha == sha
    }

    override fun hashCode(): Int {
        return sha.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(committedAt)
        parcel.writeString(message)
        parcel.writeString(sha)
        parcel.writeString(tagName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Commit> {
        const val COMMIT_TIME = "commit_time"
        const val COMMIT_MESSAGE = "commit_message"
        const val COMMIT_SHA = "commit_sha"
        const val COMMIT_TAG_NAME = "commit_tag_name"

        override fun createFromParcel(parcel: Parcel): Commit {
            return Commit(parcel)
        }

        override fun newArray(size: Int): Array<Commit?> {
            return arrayOfNulls(size)
        }
    }
}
