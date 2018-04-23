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

package com.kevalpatel2106.ci.greenbuild.base.account

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Keval on 16/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Account(

        /**
         * Unique id of the user on the CI server. For the same CI server, user id should be unique.
         */
        val userId: String,

        /**
         * Username on the CI server. For the same CI server, username should be unique.
         */
        val username: String,

        /**
         * Full name of the user.
         */
        val name: String,

        /**
         * URL of the user's avatar image.
         */
        var avatarUrl: String? = null,

        /**
         * Base url off the CI server. Using URL, application can identify which endpoint to call.
         */
        val serverUrl: String,

        /**
         * URL of the user's avatar image.
         */
        var accessToken: String
) : Parcelable {

    /**
     * Unique local id of the user account that is assures that no two account from different ci providers
     * have the same [accountId].
     */
    val accountId: String = prepareAccountId(userId, serverUrl)

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    /**
     * If two objects are having the same [accountId], they are the same.
     */
    override fun equals(other: Any?): Boolean {
        other?.let {
            if (other is Account) {
                return other.accountId == accountId
            }
        }
        return false
    }

    override fun hashCode(): Int {
        return accountId.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(avatarUrl)
        parcel.writeString(serverUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account> {
        fun prepareAccountId(userId: String, serverUrl: String): String = "$userId-$serverUrl"

        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }
    }
}
