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
 * Created by Keval on 06/05/18.
 * Kotlin bin to hold the basic information about the commit in the [Repo].
 *
 * @constructor Public constructor.
 * @param sha Unique SHA off the commit.
 * @param committedAt Commit time in milliseconds.
 * @param message Commit message. The commit message should never be null.
 * @param tagName Name of the tag if the commit is associate with any tag, otherwise it will be null.
 * @param repo [Repo] in which this commit is made.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Commit(
        val committedAt: String? = null,

        val message: String,

        val sha: String,

        val tagName: String?,

        val repo: Repo
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Repo::class.java.classLoader))

    override fun equals(other: Any?): Boolean {
        return other != null && other is Commit && other.sha == sha && other.repo.id == repo.id
    }

    override fun hashCode(): Int {
        return sha.hashCode() * 10 + repo.id.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(committedAt)
        parcel.writeString(message)
        parcel.writeString(sha)
        parcel.writeString(tagName)
        parcel.writeParcelable(repo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Commit> {
        override fun createFromParcel(parcel: Parcel): Commit {
            return Commit(parcel)
        }

        override fun newArray(size: Int): Array<Commit?> {
            return arrayOfNulls(size)
        }
    }

}