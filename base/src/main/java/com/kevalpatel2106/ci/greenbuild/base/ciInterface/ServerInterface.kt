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
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Build
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.BuildSortBy
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.BuildState
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Cache
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Cron
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Branch
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.EnvVars
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Repo
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.RepoSortBy
import io.reactivex.Observable

/**
 * Created by Keval on 16/04/18.
 * This class defines basic interface between the application and the CI server.
 *
 * @constructor Public constructor to provide the [accessToken] off the account.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
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
     * Get the branches for the repo.
     */
    abstract fun getBranches(page: Int, repoId: String): Observable<Page<Branch>>

    /**
     * Get the list of [Repo] for the account.
     */
    abstract fun getRepoList(
            page: Int,
            repoSortBy: RepoSortBy,
            showOnlyPrivate: Boolean
    ): Observable<Page<Repo>>

    /**
     * Get the list of recent [Build].
     */
    abstract fun getRecentBuildsList(
            page: Int,
            repoSortBy: BuildSortBy,
            buildState: BuildState? = null
    ): Observable<Page<Build>>

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


    /**
     * Delete [EnvVars] with [envVarId] id for the given [Repo].It will return the [Observable] with the
     * count of deleted [EnvVars] items.
     */
    abstract fun deleteEnvironmentVariable(
            repoId: String,
            envVarId: String
    ): Observable<Int>

    /**
     * Edit the [EnvVars] which has id [envVarId] and repository id [repoId]. This will return the
     * [Observable] with the updated [EnvVars].
     */
    abstract fun editEnvVariable(
            repoId: String,
            envVarId: String,
            newName: String,
            newValue: String,
            isPublic: Boolean
    ): Observable<EnvVars>


    /**
     * Get the list of [Cache] for the given [Repo]. It will return the [Observable] with the
     * paginated list of [Cache].
     */
    abstract fun getCachesList(
            page: Int,
            repoId: String
    ): Observable<Page<Cache>>

    /**
     * Delete [Cache] for [branchName] for the given [Repo]. It will return the [Observable] with the
     * count of deleted [Cache] items.
     */
    abstract fun deleteCache(
            repoId: String,
            branchName: String
    ): Observable<Int>

    /**
     * Get the list of [Cron] for the given [Repo]. It will return the [Observable] with the
     * paginated list of [Cron].
     */
    abstract fun getCronsList(
            page: Int,
            repoId: String
    ): Observable<Page<Cron>>

    /**
     * Start [Cron] with [cronId]. It will return the [Observable] with the id of the [Cron] that
     * is started.
     */
    abstract fun startCronManually(
            cronId: String,
            repoId: String
    ): Observable<String>

    /**
     * Delete [Cron] with [cronId]. It will return the [Observable] with the id of the [Cron] that
     * was deleted
     */
    abstract fun deleteCron(
            cronId: String,
            repoId: String
    ): Observable<String>

    /**
     * Schedule new [Cron] for the branch with [branchName] and with [interval]. Use [dontRunIfRecentlyBuilt]
     * to don't run the cron if the build vor that branch recently completed.
     *
     * Note that all the CI platform does not support [dontRunIfRecentlyBuilt]. Check
     * [CompatibilityCheck.isDontRunIfRecentBuildExistSupported] to check if CI platform has support
     * for [dontRunIfRecentlyBuilt] flag.
     *
     * @return It will return the [Observable] with the id of the [Cron] that was deleted.
     */
    abstract fun scheduleCron(
            repoId: String,
            branchName: String,
            interval: String,
            dontRunIfRecentlyBuilt: Boolean
    ): Observable<Cron>


    /**
     * Method to provide the list of all the supported intervals.
     *
     * @return [ArrayList] of all the supported cron intervals.
     */
    abstract fun supportedCronIntervals(): Observable<ArrayList<String>>


    companion object {

        /**
         * Number of items in the single page of the list. This value is constant. Never change!!!
         */
        const val PAGE_SIZE = 20
    }
}
