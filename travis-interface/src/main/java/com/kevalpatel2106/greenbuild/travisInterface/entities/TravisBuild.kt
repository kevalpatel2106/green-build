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
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.Build


internal data class TravisBuild(

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("number")
        val number: String,

        @field:SerializedName("state")
        val state: String,

        @field:SerializedName("duration")
        val duration: Int,

        @field:SerializedName("previous_state")
        val previousState: String? = null,

        @field:SerializedName("finished_at")
        val finishedAt: String? = null,

        @field:SerializedName("jobs")
        val jobs: List<TravisJob>? = null,

        @field:SerializedName("commit")
        val commit: TravisCommit? = null,

        @field:SerializedName("repository")
        val repository: TravisRepo,

        @field:SerializedName("branch")
        val branch: TravisBranch,

        @field:SerializedName("created_by")
        val createdBy: TravisAuthor,

        @field:SerializedName("event_type")
        val eventType: String,

        @field:SerializedName("pull_request_title")
        val pullRequestTitle: String? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("@representation")
        val representation: String? = null,

        @field:SerializedName("pull_request_number")
        val pullRequestNumber: String? = null,

        @field:SerializedName("@permissions")
        val permissions: BuildPermissions,

        @field:SerializedName("started_at")
        val startedAt: String? = null
) {

    fun toBuild(): Build {
        return Build(
                id = id.toLong(),
                state = state,
                branchName = branch.name,
                duration = duration,
                eventType = eventType,
                number = number,
                finishedAt = finishedAt,
                startedAt = startedAt
        )
    }

    data class BuildPermissions(

            @field:SerializedName("cancel")
            val cancel: Boolean,

            @field:SerializedName("read")
            val read: Boolean,

            @field:SerializedName("restart")
            val restart: Boolean
    )
}
