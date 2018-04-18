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

package com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo

/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
data class Repo(

        val id: String,

        val name: String,

        val description: String? = null,

        val isPrivate: Boolean,

        val isEnabledForCi: Boolean,

        val language: String? = null,

        val owner: Owner,

        val defaultBranch: String? = null
) {

    data class Owner(
            val name: String,
            val avatar: String? = null
    )
}
