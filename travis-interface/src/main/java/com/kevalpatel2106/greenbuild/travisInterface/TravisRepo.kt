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

package com.kevalpatel2106.greenbuild.travisInterface

import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.Repo
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
internal data class TravisRepo(

        @field:SerializedName("owner")
        val owner: Owner,

        @field:SerializedName("private")
        val isPrivate: Boolean,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("active")
        val isEnabledOnCi: Boolean,

        @field:SerializedName("active_on_org")
        val activeOnOrg: Any? = null,

        @field:SerializedName("starred")
        val starred: Boolean? = null,

        @field:SerializedName("@permissions")
        val permissions: Permissions? = null,

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("default_branch")
        val defaultBranch: DefaultBranch? = null,

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("slug")
        val slug: String,

        @field:SerializedName("github_language")
        val githubLanguage: String? = null
) {

    internal fun toReop(): Repo {
        return Repo(
                id = id.toString(),
                name = name,
                description = description,
                defaultBranch = defaultBranch?.name,
                isEnabledForCi = isEnabledOnCi,
                isPrivate = isPrivate,
                language = githubLanguage,
                owner = Repo.Owner(
                        name = owner.login,
                        avatar = null
                )
        )
    }

    internal data class Permissions(

            @field:SerializedName("unstar")
            val unstar: Boolean,

            @field:SerializedName("delete_key_pair")
            val deleteKeyPair: Boolean,

            @field:SerializedName("read")
            val read: Boolean,

            @field:SerializedName("star")
            val star: Boolean,

            @field:SerializedName("create_key_pair")
            val createKeyPair: Boolean,

            @field:SerializedName("activate")
            val activate: Boolean,

            @field:SerializedName("admin")
            val admin: Boolean,

            @field:SerializedName("create_env_var")
            val createEnvVar: Boolean,

            @field:SerializedName("create_cron")
            val createCron: Boolean,

            @field:SerializedName("create_request")
            val createRequest: Boolean,

            @field:SerializedName("deactivate")
            val deactivate: Boolean
    )

    internal data class DefaultBranch(

            @field:SerializedName("name")
            val name: String,

            @field:SerializedName("@href")
            val href: String? = null
    )

    internal data class Owner(

            @field:SerializedName("id")
            val id: Int,

            @field:SerializedName("login")
            val login: String,

            @field:SerializedName("@href")
            val href: String
    )
}