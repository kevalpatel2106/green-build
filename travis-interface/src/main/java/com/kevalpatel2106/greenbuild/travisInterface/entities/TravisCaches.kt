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

package com.kevalpatel2106.greenbuild.travisInterface.entities

import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.grrenbuild.entities.Cache
import com.kevalpatel2106.greenbuild.utils.ConversationUtils
import com.kevalpatel2106.grrenbuild.entities.Branch

internal data class TravisCaches(

        @field:SerializedName("size")
        val size: Int,

        @field:SerializedName("repo")
        val repo: TravisRepo,

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("repository_id")
        val repositoryId: Int,

        @field:SerializedName("commitBranch")
        val branch: String,

        @field:SerializedName("last_modified")
        val lastModified: String        //RFC 3339 (e.g. 2018-04-03T11:32:26.553Z)
) {

    fun toCache(): Cache {
        return Cache(
                localId = 0,
                name = name,
                size = size.toLong(),
                branch = Branch(
                        name = branch,
                        repoId = repositoryId.toString(),
                        isDefault = false
                ),
                lastModified = ConversationUtils.rfc3339ToMills(lastModified),
                repoId = repositoryId.toString()
        )
    }
}
