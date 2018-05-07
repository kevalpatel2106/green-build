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
import com.kevalpatel2106.grrenbuild.entities.Cron
import com.kevalpatel2106.greenbuild.utils.ConversationUtils

internal data class TravisCron(

        @field:SerializedName("next_run")
        val nextRun: String,

        @field:SerializedName("dont_run_if_recent_build_exists")
        val dontRunIfRecentBuildExists: Boolean,

        @field:SerializedName("@permissions")
        val permissions: CronPermissions,

        @field:SerializedName("created_at")
        val createdAt: String,

        @field:SerializedName("interval")
        val interval: String,

        @field:SerializedName("last_run")
        val lastRun: String?,

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("repository")
        val repository: TravisRepo,

        @field:SerializedName("commitBranch")
        val branch: TravisBranch
) {

    internal data class CronPermissions(

            @field:SerializedName("canRead")
            val read: Boolean,

            @field:SerializedName("start")
            val start: Boolean,

            @field:SerializedName("delete")
            val delete: Boolean
    )


    fun toCron(): Cron {
        return Cron(
                localId = 0,
                id = id.toString(),
                nextRun = ConversationUtils.rfc3339ToMills(nextRun),
                lastRun = if (lastRun == null) 0 else ConversationUtils.rfc3339ToMills(lastRun),
                branch = branch.toBranch(repository.id.toString()),
                createdAt = ConversationUtils.rfc3339ToMills(createdAt),
                isRunIfRecentlyBuilt = dontRunIfRecentBuildExists,
                canIDelete = permissions.delete,
                canIStartCron = permissions.start,
                repoId = repository.id.toString()
        )
    }
}
