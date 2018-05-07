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
 * Created by Keval on 22/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class EnvVars(

        val id: String,

        val name: String,

        val value: String?,

        val public: Boolean
) : Parcelable {

    var isDeleting = false

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte())

    override fun equals(other: Any?): Boolean {
        return other != null && other is EnvVars && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(value)
        parcel.writeByte(if (public) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EnvVars> {
        override fun createFromParcel(parcel: Parcel): EnvVars {
            return EnvVars(parcel)
        }

        override fun newArray(size: Int): Array<EnvVars?> {
            return arrayOfNulls(size)
        }
    }
}
