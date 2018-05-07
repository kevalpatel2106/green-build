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

@Entity(tableName = Cache.CACHE_TABLE)
data class Cache(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = CACHE_LOCAL_ID)
        var localId: Long,

        @ColumnInfo(name = CACHE_SIZE)
        var size: Long,

        @ColumnInfo(name = CACHE_NAME)
        var name: String,

        @Embedded(prefix = "caches_")
        var branch: Branch,

        @ColumnInfo(name = CACHE_LAST_MODIFIED)
        var lastModified: Long,

        @ColumnInfo(name = CACHE_REPO_ID)
        var repoId: String
) : Parcelable {

    /**
     * Bool to set true if the application is currently deleting the [Cache]. Default value is false.
     */
    var isDeleting = false

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readParcelable(Branch::class.java.classLoader),
            parcel.readLong(),
            parcel.readString()) {
        isDeleting = parcel.readByte() != 0.toByte()
    }

    override fun equals(other: Any?): Boolean {
        return other != null
                && other is Cache
                && other.repoId == repoId
                && other.branch == branch
    }

    override fun hashCode(): Int {
        return repoId.hashCode() * 10 + branch.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(localId)
        parcel.writeLong(size)
        parcel.writeString(name)
        parcel.writeParcelable(branch, flags)
        parcel.writeLong(lastModified)
        parcel.writeString(repoId)
        parcel.writeByte(if (isDeleting) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cache> {
        const val CACHE_TABLE = "cache_table"
        const val CACHE_LOCAL_ID = "cache_local_id"
        const val CACHE_SIZE = "cache_size"
        const val CACHE_NAME = "cache_name"
        const val CACHE_LAST_MODIFIED = "cache_last_modified"
        const val CACHE_REPO_ID = "cache_repo_id"

        override fun createFromParcel(parcel: Parcel): Cache {
            return Cache(parcel)
        }

        override fun newArray(size: Int): Array<Cache?> {
            return arrayOfNulls(size)
        }
    }
}
