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
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Entity(tableName = Repo.REPO_TABLE_NAME)
data class Repo(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = REPO_LOCAL_ID)
        var localId: Long,

        @ColumnInfo(name = REPO_ID)
        var id: String,

        @ColumnInfo(name = REPO_NAME)
        var name: String,

        @ColumnInfo(name = REPO_DESCRIPTION)
        var description: String?,

        @ColumnInfo(name = REPO_IS_PRIVATE)
        var isPrivate: Boolean,

        @ColumnInfo(name = REPO_IS_CI_ENABLED)
        var isEnabledForCi: Boolean,

        @ColumnInfo(name = REPO_LANGUAGE)
        var language: String?,

        @Embedded(prefix = "repos_")
        var owner: Author,

        @Embedded(prefix = "repos_")
        var defaultBranch: Branch?,

        @Embedded(prefix = "repos_")
        var permissions: Permissions?
) : Parcelable {

    @Ignore
    var lastBuild: Build? = null

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readParcelable(Author::class.java.classLoader),
            parcel.readParcelable(Branch::class.java.classLoader),
            parcel.readParcelable(Permissions::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(localId)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeByte(if (isPrivate) 1 else 0)
        parcel.writeByte(if (isEnabledForCi) 1 else 0)
        parcel.writeString(language)
        parcel.writeParcelable(owner, flags)
        parcel.writeParcelable(defaultBranch, flags)
        parcel.writeParcelable(permissions, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repo> {
        const val REPO_TABLE_NAME = "table_name"
        const val REPO_LOCAL_ID = "repo_local_id"
        const val REPO_ID = "repo_id"
        const val REPO_NAME = "repo_name"
        const val REPO_DESCRIPTION = "repo_description"
        const val REPO_IS_PRIVATE = "repo_is_private"
        const val REPO_IS_CI_ENABLED = "repo_is_ci_enabled"
        const val REPO_LANGUAGE = "repo_language"

        override fun createFromParcel(parcel: Parcel): Repo {
            return Repo(parcel)
        }

        override fun newArray(size: Int): Array<Repo?> {
            return arrayOfNulls(size)
        }
    }

}

data class Permissions(

        @ColumnInfo(name = REPO_CAN_READ)
        var canRead: Boolean,

        @ColumnInfo(name = REPO_CAN_ENABLE_CI_BUILD)
        var canEnableCIBuild: Boolean,

        @ColumnInfo(name = REPO_IS_ADMIN)
        var isAdmin: Boolean,

        @ColumnInfo(name = REPO_CAN_CREATE_ENV_VAR)
        var canCreateEnvVar: Boolean,

        @ColumnInfo(name = REPO_CAN_SCHEDULE_CRON)
        var canScheduleCron: Boolean,

        @ColumnInfo(name = REPO_CAN_REQUEST_BUILD)
        var canRequestBuild: Boolean,

        @ColumnInfo(name = REPO_CAN_DISABLE_CI_BUILD)
        var canDisableCIBuild: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (canRead) 1 else 0)
        parcel.writeByte(if (canEnableCIBuild) 1 else 0)
        parcel.writeByte(if (isAdmin) 1 else 0)
        parcel.writeByte(if (canCreateEnvVar) 1 else 0)
        parcel.writeByte(if (canScheduleCron) 1 else 0)
        parcel.writeByte(if (canRequestBuild) 1 else 0)
        parcel.writeByte(if (canDisableCIBuild) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Permissions> {
        const val REPO_CAN_READ = "repo_can_read"
        const val REPO_CAN_ENABLE_CI_BUILD = "repo_can_enable_ci_build"
        const val REPO_IS_ADMIN = "repo_is_admin"
        const val REPO_CAN_CREATE_ENV_VAR = "repo_can_create_env_var"
        const val REPO_CAN_SCHEDULE_CRON = "repo_can_schedule_cron"
        const val REPO_CAN_REQUEST_BUILD = "repo_can_request_build"
        const val REPO_CAN_DISABLE_CI_BUILD = "repo_can_disable_ci_build"

        override fun createFromParcel(parcel: Parcel): Permissions {
            return Permissions(parcel)
        }

        override fun newArray(size: Int): Array<Permissions?> {
            return arrayOfNulls(size)
        }
    }
}
