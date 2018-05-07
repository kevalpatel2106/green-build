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

data class Cache(

        val size: Long,

        val name: String? = null,

        val branchName: String,

        val lastModified: Long,

        val repo: Repo
) : Parcelable {

    /**
     * Bool to set true if the application is currently deleting the [Cache]. Default value is false.
     */
    var isDeleting = false

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readParcelable(Repo::class.java.classLoader)) {
        isDeleting = parcel.readByte() != 0.toByte()
    }

    override fun equals(other: Any?): Boolean {
        return other != null
                && other is Cache
                && other.repo.id == repo.id
                && other.branchName == branchName
    }

    override fun hashCode(): Int {
        return repo.id.hashCode() * 10 + branchName.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(size)
        parcel.writeString(name)
        parcel.writeString(branchName)
        parcel.writeLong(lastModified)
        parcel.writeParcelable(repo, flags)
        parcel.writeByte(if (isDeleting) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cache> {
        override fun createFromParcel(parcel: Parcel): Cache {
            return Cache(parcel)
        }

        override fun newArray(size: Int): Array<Cache?> {
            return arrayOfNulls(size)
        }
    }
}
