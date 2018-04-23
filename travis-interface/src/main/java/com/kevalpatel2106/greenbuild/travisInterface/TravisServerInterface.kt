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

import com.kevalpatel2106.ci.greenbuild.base.account.Account
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CiServer
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.Page
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.Build
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildSortBy
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildState
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.envVars.EnvVars
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.Repo
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.RepoSortBy
import com.kevalpatel2106.ci.greenbuild.base.network.NetworkApi
import com.kevalpatel2106.greenbuild.travisInterface.authentication.TravisAuthenticationActivity
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Keval on 16/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
class TravisServerInterface internal constructor(
        private val baseUrl: String,
        accessToken: String) : ServerInterface(accessToken) {

    private val travisEndpoints = NetworkApi(accessToken)
            .getRetrofitClient(baseUrl)
            .create(TravisEndpoints::class.java)

    companion object {

        /**
         * Base url for accessing the API for travis-ci.org.
         *
         * @see <a href="https://developer.travis-ci.org/gettingstarted">API Doc</a>
         */
        const val TRAVIS_CI_ORG = "https://api.travis-ci.org"

        /**
         * Base url for accessing the API for travis-ci.com.
         *
         * @see <a href="https://developer.travis-ci.org/gettingstarted">API Doc</a>
         */
        const val TRAVIS_CI_COM = "https://api.travis-ci.com"

        fun get(baseUrl: String, accessToken: String): TravisServerInterface? {

            return when {
                baseUrl == TRAVIS_CI_ORG -> {
                    TravisServerInterface(
                            baseUrl = TRAVIS_CI_ORG,
                            accessToken = accessToken
                    )
                }
                baseUrl == TRAVIS_CI_COM -> {
                    TravisServerInterface(
                            baseUrl = TRAVIS_CI_COM,
                            accessToken = accessToken
                    )
                }
                baseUrl.startsWith("https://travis.") && baseUrl.endsWith("/api/") -> {
                    TravisServerInterface(
                            baseUrl = baseUrl,
                            accessToken = accessToken
                    )
                }
                else -> {
                    Timber.i("Not a travis ci server: $baseUrl")
                    null
                }
            }
        }

        fun getCiServers(application: BaseApplication): ArrayList<CiServer> {
            val ciServers = ArrayList<CiServer>(3)

            ciServers.add(CiServer(
                    icon = R.drawable.logo_travis_ci_org,
                    name = "Travis CI (Open Source repo)",
                    description = "Travis continuous integration for open source projects on GitHub.",
                    domain = "https://travis-ci.org",
                    onClick = {
                        TravisAuthenticationActivity.launch(application, TravisServerInterface.TRAVIS_CI_ORG)
                    }))
            ciServers.add(CiServer(
                    icon = R.drawable.logo_travis_ci_com,
                    name = "Travis CI (Private repo)",
                    description = "Travis continuous integration for private repositories on GitHub.",
                    domain = "https://travis-ci.com",
                    onClick = {
                        TravisAuthenticationActivity.launch(application, TravisServerInterface.TRAVIS_CI_COM)
                    }))
            ciServers.add(CiServer(
                    icon = R.drawable.logo_travis_ci_enterprice,
                    name = "Travis CI (Enterprise)",
                    description = "Self hosted continuous integration from Travis CI.",
                    domain = null,
                    onClick = {
                        TravisAuthenticationActivity.launch(application, null)
                    }))

            return ciServers
        }
    }


    override fun getBaseUrl(): String {
        return baseUrl
    }

    /**
     * Get the information of the user based on the [accessToken] provided. This method will use
     * [TravisEndpoints.getMyProfile] endpoint to get the user information in [ResponseMyAccount] object.
     * Once the object is received, [ResponseMyAccount] will be converted to [Account].
     *
     * @see [ResponseMyAccount.getAccount]
     */
    override fun getMyAccount(): Observable<Account> {
        return travisEndpoints
                .getMyProfile()
                .map { it.getAccount(baseUrl, accessToken) }
    }

    override fun getRepoList(page: Int,
                             repoSortBy: RepoSortBy,
                             showOnlyPrivate: Boolean): Observable<Page<Repo>> {

        val sortByQuery = when (repoSortBy) {
            RepoSortBy.NAME_ASC -> "name"
            RepoSortBy.NAME_DESC -> "name:desc"
            RepoSortBy.LAST_BUILD_TIME_ASC -> "default_branch.last_build"
            RepoSortBy.LAST_BUILD_TIME_DESC -> "default_branch.last_build:desc"
        }

        return travisEndpoints
                .getMyRepos(
                        sortBy = sortByQuery,
                        onlyActive = true,
                        offset = (page - 1) * PAGE_SIZE,
                        onlyPrivate = showOnlyPrivate
                ).map {
                    val repoList = ArrayList<Repo>(it.repositories.size)
                    it.repositories.forEach { repoList.add(it.toRepo()) }
                    return@map Page(
                            hasNext = !it.pagination.isLast,
                            list = repoList
                    )
                }
    }

    override fun getBuildList(page: Int,
                              repoId: String,
                              repoSortBy: BuildSortBy,
                              buildState: BuildState?): Observable<Page<Build>> {
        val sortByQuery = when (repoSortBy) {
            BuildSortBy.STARTED_AT_ASC -> "started_at"
            BuildSortBy.STARTED_AT_DESC -> "started_at:desc"
            BuildSortBy.FINISHED_AT_ASC -> "finished_at"
            BuildSortBy.FINISHED_AT_DESC -> "finished_at:desc"
        }

        val state = when (buildState) {
            BuildState.CANCELED -> Constants.CANCEL_BUILD
            BuildState.PASSED -> Constants.PASSED_BUILD
            BuildState.RUNNING -> Constants.RUNNING_BUILD
            BuildState.FAILED -> Constants.FAILED_BUILD
            BuildState.ERRORED -> Constants.ERRORED_BUILD
            BuildState.BOOTING -> Constants.BOOTING_BUILD
            null -> null
            else -> null
        }

        return travisEndpoints
                .getBuildsForRepo(
                        sortBy = sortByQuery,
                        offset = (page - 1) * PAGE_SIZE,
                        repoId = repoId,
                        state = state
                ).map {
                    val buildList = ArrayList<Build>(it.builds.size)
                    it.builds.forEach { buildList.add(it.toBuild()) }
                    return@map Page(
                            hasNext = !it.pagination.isLast,
                            list = buildList
                    )
                }
    }

    override fun getEnvironmentVariablesList(page: Int,
                                             repoId: String): Observable<Page<EnvVars>> {
        return travisEndpoints
                .getEnvVariablesForRepo(repoId = repoId)
                .map {
                    val buildList = ArrayList<EnvVars>(it.envVars.size)
                    it.envVars.forEach { buildList.add(it.toEnvVars()) }
                    return@map Page(
                            hasNext = false, /* This api is not designed for pagination */
                            list = buildList
                    )
                }
    }

}
