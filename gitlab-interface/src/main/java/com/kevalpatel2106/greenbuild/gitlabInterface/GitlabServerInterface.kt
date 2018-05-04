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

package com.kevalpatel2106.greenbuild.gitlabInterface

import android.content.Context
import com.kevalpatel2106.ci.greenbuild.base.account.Account
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.Page
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.*
import com.kevalpatel2106.greenbuild.gitlabInterface.authentication.GitlabAuthenticationActivity
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Kevalpatel2106 on 04-May-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class GitlabServerInterface internal constructor(
        private val application: BaseApplication,
        private val baseUrl: String,
        accessToken: String
) : ServerInterface(accessToken) {
    /**
     * Get the type of the ci server for which this interface was implemented. e.g. If this [ServerInterface]
     * is implemented for travis-ci.org, the returned base url will be travis-ci.org. This is to
     * uniquely identify the [ServerInterface] of different CI services.
     */
    override fun getBaseUrl(): String = baseUrl

    /**
     * Get the information of the user based on the [accessToken] provided. Caller can observe the
     * [Observable] to get the [Account] information.
     */
    override fun getMyAccount(): Observable<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the branches for the repo.
     */
    override fun getBranches(page: Int, repoId: String): Observable<Page<Branch>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the list of [Repo] for the account.
     */
    override fun getRepoList(page: Int, repoSortBy: RepoSortBy, showOnlyPrivate: Boolean): Observable<Page<Repo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the list of recent [Build].
     */
    override fun getRecentBuildsList(page: Int, repoSortBy: BuildSortBy, buildState: BuildState?): Observable<Page<Build>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the list of [Build] for the given [Repo].
     */
    override fun getBuildList(page: Int, repoId: String, repoSortBy: BuildSortBy, buildState: BuildState?): Observable<Page<Build>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the list of [EnvVars] for the given [Repo].
     */
    override fun getEnvironmentVariablesList(page: Int, repoId: String): Observable<Page<EnvVars>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Delete [EnvVars] with [envVarId] id for the given [Repo].It will return the [Observable] with the
     * count of deleted [EnvVars] items.
     */
    override fun deleteEnvironmentVariable(repoId: String, envVarId: String): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Edit the [EnvVars] which has id [envVarId] and repository id [repoId]. This will return the
     * [Observable] with the updated [EnvVars].
     */
    override fun editEnvVariable(repoId: String, envVarId: String, newName: String, newValue: String, isPublic: Boolean): Observable<EnvVars> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the list of [Cache] for the given [Repo]. It will return the [Observable] with the
     * paginated list of [Cache].
     */
    override fun getCachesList(page: Int, repoId: String): Observable<Page<Cache>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Delete [Cache] for [branchName] for the given [Repo]. It will return the [Observable] with the
     * count of deleted [Cache] items.
     */
    override fun deleteCache(repoId: String, branchName: String): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Get the list of [Cron] for the given [Repo]. It will return the [Observable] with the
     * paginated list of [Cron].
     */
    override fun getCronsList(page: Int, repoId: String): Observable<Page<Cron>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Start [Cron] with [cronId]. It will return the [Observable] with the id of the [Cron] that
     * is started.
     */
    override fun startCronManually(cronId: String, repoId: String): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Delete [Cron] with [cronId]. It will return the [Observable] with the id of the [Cron] that
     * was deleted
     */
    override fun deleteCron(cronId: String, repoId: String): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
    override fun scheduleCron(repoId: String, branchName: String, interval: String, dontRunIfRecentlyBuilt: Boolean): Observable<Cron> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to provide the list of all the supported intervals.
     *
     * @return [ArrayList] of all the supported cron intervals.
     */
    override fun supportedCronIntervals(): Observable<ArrayList<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Restart the [build]. You can only restart the build which has status other than
     * [BuildState.RUNNING] or [BuildState.BOOTING].
     *
     * @return It will return the [Observable] with the id of the new build.
     */
    override fun restartBuild(build: Build): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Abort the [build]. You can abort the build that are in [BuildState.RUNNING] only.
     *
     * @return It will return the [Observable] with the id of the new build.
     */
    override fun abortBuild(build: Build): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        fun get(application: BaseApplication, baseUrl: String, accessToken: String): GitlabServerInterface? {

            return when {
                baseUrl == Constants.GITLAB_API -> {
                    GitlabServerInterface(
                            application = application,
                            baseUrl = Constants.GITLAB_API,
                            accessToken = accessToken
                    )
                }
                baseUrl.startsWith("https://gitlab.") && baseUrl.endsWith("/api/v4") -> {
                    GitlabServerInterface(
                            application = application,
                            baseUrl = baseUrl,
                            accessToken = accessToken
                    )
                }
                else -> {
                    Timber.i("Not a gitlab ci server: $baseUrl")
                    null
                }
            }
        }

        fun getCiServers(activity: Context): ArrayList<CiServer> {
            val ciServers = ArrayList<CiServer>(3)

            ciServers.add(CiServer(
                    icon = R.drawable.gitlab_logo,
                    name = activity.getString(R.string.ci_provider_name_travis_org),
                    description = activity.getString(R.string.ci_provider_description_travis_org),
                    domain = activity.getString(R.string.gitlab_domain),
                    onClick = {
                        GitlabAuthenticationActivity.launch(
                                context = activity,
                                options = it,
                                ciName = "Gitlab CI",
                                ciIcon = R.drawable.gitlab_logo,
                                baseUrl = Constants.GITLAB_API
                        )
                    }))
            ciServers.add(CiServer(
                    icon = R.drawable.gitlab_logo,
                    name = activity.getString(R.string.ci_provider_name_travis_enterprice),
                    description = activity.getString(R.string.ci_provider_description_travis_enterprice),
                    domain = null,
                    onClick = {
                        GitlabAuthenticationActivity.launch(
                                context = activity,
                                options = it,
                                ciName = "Gitlab CI (Enterprise)",
                                ciIcon = R.drawable.gitlab_logo,
                                baseUrl = null
                        )
                    }))

            return ciServers
        }
    }

}
