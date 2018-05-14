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
 * Created by Kevalpatel2106 on 07-May-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

/**
 * The branch for the [Repo].
 *
 * @constructor Public constructor.
 * @param name Name of the branch
 * @param isDefault True if the branch is the default git branch. (Generally "master" is the default
 * branch.) The default value is false.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Branch(

        @ColumnInfo(name = BRANCH_REPO_ID)
        var repoId: String,

        @ColumnInfo(name = BRANCH_NAME)
        var name: String,

        @ColumnInfo(name = BRANCH_IS_DEFAULT)
        var isDefault: Boolean
) : Parcelable {

    /**
     * Boolean to indicate weather this branch is selected or not. True if the branch is selected else
     * false. Default value is false.
     */
    var isSelected = false

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(repoId)
        parcel.writeString(name)
        parcel.writeByte(if (isDefault) 1 else 0)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Branch> {
        const val BRANCH_REPO_ID = "branch_repo_id"
        const val BRANCH_NAME = "branch_name"
        const val BRANCH_IS_DEFAULT = "branch_is_default"

        override fun createFromParcel(parcel: Parcel): Branch {
            return Branch(parcel)
        }

        override fun newArray(size: Int): Array<Branch?> {
            return arrayOfNulls(size)
        }
    }
}
