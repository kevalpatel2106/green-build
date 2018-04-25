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

package com.kevalpatel2106.greenbuild.travisInterface.response

import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.ci.greenbuild.base.account.Account

internal data class ResponseMyAccount(

        @field:SerializedName("is_syncing")
        val isSyncing: Boolean,

        @field:SerializedName("avatar_url")
        val avatarUrl: String? = null,

        @field:SerializedName("@type")
        val type: String,

        @field:SerializedName("@representation")
        val representation: String,

        @field:SerializedName("@envVarsPermissions")
        val permissions: AccountPermissions,

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("github_id")
        val githubId: Int,

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("login")
        val login: String,

        @field:SerializedName("synced_at")
        val syncedAt: String? = null
) {

    internal data class AccountPermissions(

            @field:SerializedName("canRead")
            val read: Boolean,

            @field:SerializedName("sync")
            val sync: Boolean
    )

    /**
     * Convert this [ResponseMyAccount] to [Account].
     */
    internal fun getAccount(serverUrl: String, accessToken: String): Account {
        return Account(
                userId = id.toString(),
                username = login,
                name = name,
                avatarUrl = avatarUrl,
                serverUrl = serverUrl,
                accessToken = accessToken
        )
    }
}
