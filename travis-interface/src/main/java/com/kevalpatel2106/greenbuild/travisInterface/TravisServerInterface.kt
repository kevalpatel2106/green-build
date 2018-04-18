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
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.Repo
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.SortBy
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
     * [TravisEndpoints.getMyProfile] endpoint to get the user information in [TravisUser] object.
     * Once the object is received, [TravisUser] will be converted to [Account].
     *
     * @see [TravisUser.getAccount]
     */
    override fun getMyAccount(): Observable<Account> {
        return travisEndpoints
                .getMyProfile()
                .map { it.getAccount(baseUrl, accessToken) }
    }

    override fun getRepoList(page: Int,
                             sortBy: SortBy,
                             showOnlyPrivate: Boolean): Observable<Page<ArrayList<Repo>>> {

        val sortByQuery = when (sortBy) {
            SortBy.NAME_ASC -> "name"
            SortBy.NAME_DESC -> "name:desc"
            SortBy.LAST_BUILD_TIME_ASC -> "default_branch.last_build"
            SortBy.LAST_BUILD_TIME_DESC -> "default_branch.last_build:desc"
        }

        return travisEndpoints
                .getMyRepos(
                        sortBy = sortByQuery,
                        onlyActive = true,
                        onlyPrivate = showOnlyPrivate
                ).map {
                    val repoList = ArrayList<Repo>(it.repositories.size)
                    it.repositories.forEach { repoList.add(it.toReop()) }
                    return@map Page(
                            hasNext = !it.pagination.isLast,
                            list = repoList
                    )
                }
    }

}
