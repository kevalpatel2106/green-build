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
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Keval on 22/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Entity(tableName = EnvVars.ENV_VAR_TABLE_NAME)
data class EnvVars(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = ENV_VAR_LOCAL_ID)
        var localId: Long,

        @ColumnInfo(name = ENV_VAR_ID)
        var id: String,

        @ColumnInfo(name = ENV_VAR_REPO_ID)
        var repoId: String,

        @ColumnInfo(name = ENV_VAR_NAME)
        var name: String,

        @ColumnInfo(name = ENV_VAR_VALUE)
        var value: String?,

        @ColumnInfo(name = ENV_VAR_IS_PUBLIC)
        var public: Boolean
) : Parcelable {

    var isDeleting = false

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
        isDeleting = parcel.readByte() != 0.toByte()
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is EnvVars && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
   }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(localId)
        parcel.writeString(id)
        parcel.writeString(repoId)
        parcel.writeString(name)
        parcel.writeString(value)
        parcel.writeByte(if (public) 1 else 0)
        parcel.writeByte(if (isDeleting) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EnvVars> {
        const val ENV_VAR_TABLE_NAME = "env_vars"
        const val ENV_VAR_LOCAL_ID = "env_var_local_id"
        const val ENV_VAR_ID = "env_var_id"
        const val ENV_VAR_REPO_ID = "env_var_repo_id"
        const val ENV_VAR_NAME = "env_var_name"
        const val ENV_VAR_VALUE = "env_var_value"
        const val ENV_VAR_IS_PUBLIC = "env_var_is_public"

        override fun createFromParcel(parcel: Parcel): EnvVars {
            return EnvVars(parcel)
        }

        override fun newArray(size: Int): Array<EnvVars?> {
            return arrayOfNulls(size)
        }
    }

}
