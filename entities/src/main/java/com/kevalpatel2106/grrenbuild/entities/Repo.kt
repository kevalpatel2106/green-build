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
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Repo(

        val id: String,

        val name: String,

        val description: String? = null,

        val isPrivate: Boolean,

        val isEnabledForCi: Boolean,

        val language: String? = null,

        val owner: Owner,

        val defaultBranch: String? = null,

        val permissions: Permissions?,

        val lastBuild: Build?
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readParcelable(Owner::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(Permissions::class.java.classLoader),
            parcel.readParcelable(Build::class.java.classLoader))

    data class Owner(
            val name: String,
            val avatar: String? = null
    ) : Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(avatar)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Owner> {
            override fun createFromParcel(parcel: Parcel): Owner {
                return Owner(parcel)
            }

            override fun newArray(size: Int): Array<Owner?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Permissions(

            val canRead: Boolean,

            val canEnableCIBuild: Boolean,

            val isAdmin: Boolean,

            val canCreateEnvVar: Boolean,

            val canScheduleCron: Boolean,

            val canRequestBuild: Boolean,

            val canDisableCIBuild: Boolean
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
            override fun createFromParcel(parcel: Parcel): Permissions {
                return Permissions(parcel)
            }

            override fun newArray(size: Int): Array<Permissions?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeByte(if (isPrivate) 1 else 0)
        parcel.writeByte(if (isEnabledForCi) 1 else 0)
        parcel.writeString(language)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(defaultBranch)
        parcel.writeParcelable(permissions, flags)
        parcel.writeParcelable(lastBuild, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repo> {
        override fun createFromParcel(parcel: Parcel): Repo {
            return Repo(parcel)
        }

        override fun newArray(size: Int): Array<Repo?> {
            return arrayOfNulls(size)
        }
    }
}
