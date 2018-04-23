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

package com.kevalpatel2106.ci.greenbuild.base.ciInterface

import com.kevalpatel2106.ci.greenbuild.base.account.Account
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.Build
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildSortBy
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildState
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.envVars.EnvVars
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.Repo
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.RepoSortBy
import io.reactivex.Observable

/**
 * Created by Keval on 16/04/18.
 * This class defines basic interface between the application and the CI server.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
abstract class ServerInterface(protected val accessToken: String) {

    /**
     * Get the type of the ci server for which this interface was implemented. e.g. If this [ServerInterface]
     * is implemented for travis-ci.org, the returned base url will be travis-ci.org. This is to
     * uniquely identify the [ServerInterface] of different CI services.
     */
    abstract fun getBaseUrl(): String

    /**
     * Get the information of the user based on the [accessToken] provided. Caller can observe the
     * [Observable] to get the [Account] information.
     */
    abstract fun getMyAccount(): Observable<Account>

    /**
     * Get the list of [Repo] for the account.
     */
    abstract fun getRepoList(
            page: Int,
            repoSortBy: RepoSortBy,
            showOnlyPrivate: Boolean
    ): Observable<Page<Repo>>

    /**
     * Get the list of [Build] for the given [Repo].
     */
    abstract fun getBuildList(
            page: Int,
            repoId: String,
            repoSortBy: BuildSortBy,
            buildState: BuildState? = null
    ): Observable<Page<Build>>

    /**
     * Get the list of [EnvVars] for the given [Repo].
     */
    abstract fun getEnvironmentVariablesList(
            page: Int,
            repoId: String
    ): Observable<Page<EnvVars>>


    companion object {
        const val PAGE_SIZE = 20
    }
}
