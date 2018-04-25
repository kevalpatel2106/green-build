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

package com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities

/**
 * Created by Kevalpatel2106 on 18-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class Build(

        val id: Long,

        val number: String,

        val duration: Long,

        val state: BuildState,

        val previousState: String?,

        val startedAt: Long = 0,

        val finishedAt: Long = 0,

        val triggerType: TriggerType,

        val author: Author,

        val branch: Branch,

        val commit: Commit
) {

    data class Author(
            val id: String,

            val username: String
    )

    data class Commit(
            val committedAt: String? = null,

            val message: String,

            val sha: String,

            val tagName: String?
    )
}
