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
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.Page
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.Build
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildSortBy
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildState
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.Repo
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.RepoSortBy
import com.kevalpatel2106.ci.greenbuild.base.network.NetworkApi
import io.reactivex.Observable

/**
 * Created by Keval on 16/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
class TravisServerInterface(private val baseUrl: String,
                            accessToken: String)
    : ServerInterface(accessToken) {

    private val travisEndpoints = NetworkApi(accessToken)
            .getRetrofitClient(baseUrl)
            .create(TravisEndpoints::class.java)

    companion object {

        /**
         * Get [TravisServerInterface] for travis-ci.org.
         */
        fun getTravisOrgInterface(accessToken: String) = TravisServerInterface(
                baseUrl = ServerInterface.TRAVIS_CI_ORG,
                accessToken = accessToken
        )

        /**
         * Get [TravisServerInterface] for travis-ci.com.
         */
        fun getTravisComInterface(accessToken: String) = TravisServerInterface(
                baseUrl = ServerInterface.TRAVIS_CI_COM,
                accessToken = accessToken
        )
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
            BuildState.CANCELED -> "canceled"
            BuildState.PASSED -> "passed"
            BuildState.RUNNING -> "running"
            BuildState.FAILED -> "failed"
            BuildState.ERRORED -> "aborted"
            null -> null
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
}
