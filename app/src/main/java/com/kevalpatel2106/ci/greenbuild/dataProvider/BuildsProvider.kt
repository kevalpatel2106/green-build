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

package com.kevalpatel2106.ci.greenbuild.dataProvider

import com.kevalpatel2106.ci.greenbuild.base.ciInterface.*
import com.kevalpatel2106.grrenbuild.entities.Build
import com.kevalpatel2106.grrenbuild.entities.BuildState
import com.kevalpatel2106.grrenbuild.entities.Repo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 04-May-18.
 * A class that provides the data for the [Build] to the view models and acts as a repository. This
 * class will responsible for connecting with CI platform servers and database to perform [Build]
 * operations.
 *
 * @constructor Dagger injectable public constructor.
 * @param serverInterface [ServerInterface] for making the api calls to the CI servers.
 * @param compatibilityCheck [CompatibilityCheck] to check the compatibility with the CI platform.
 * @param GBRxSchedulers [GBRxSchedulers] to provide threads.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class BuildsProvider @Inject constructor(
        private val serverInterface: ServerInterface,
        private val compatibilityCheck: CompatibilityCheck,
        private val GBRxSchedulers: GBRxSchedulers
) {
    /**
     * Get the [Observable] of the list of recent [Build].
     *
     * @return [Observable] of the list of recent [Build].
     * @param page Page number to load. (Number of records in one page is [ServerInterface.PAGE_SIZE]).
     * @param repoSortBy [BuildSortBy] to sort the [Build].
     * @param buildState [BuildState] to filter the [Build] based on their state.
     */
    fun getRecentBuildsList(
            page: Int,
            repoSortBy: BuildSortBy,
            buildState: BuildState? = null
    ): Observable<Page<Build>> {
        if (!compatibilityCheck.isRecentBuildsListSupported())
            throw IllegalStateException("Recent builds listing by repository is not supported for this CI.")

        return serverInterface.getRecentBuildsList(page, repoSortBy, buildState)
                .observeOn(GBRxSchedulers.main)
                .subscribeOn(GBRxSchedulers.network)
    }

    /**
     * Restart the [build]. You can only restart the build which has status other than
     * [BuildState.RUNNING] or [BuildState.BOOTING].
     *
     * @return It will return the [Observable] with the id of the new build.
     */
    fun restartBuild(build: Build): Observable<String>{
        if (!compatibilityCheck.isRestartBuildSupported())
            throw IllegalStateException("Restart build is not supported for this CI platform.")

        if (build.state == BuildState.RUNNING || build.state == BuildState.BOOTING)
            throw IllegalStateException("Cannot restart running or booting build")

        return serverInterface.restartBuild(build)
                .observeOn(GBRxSchedulers.main)
                .subscribeOn(GBRxSchedulers.network)
    }

    /**
     * Abort the [build]. You can abort the build that are in [BuildState.RUNNING] only.
     *
     * @return It will return the [Observable] with the id of the new build.
     */
    fun abortBuild(build: Build): Observable<String>{
        if (!compatibilityCheck.isAbortBuildSupported())
            throw IllegalStateException("Abort build is not supported for this CI platform.")

        if (build.state != BuildState.RUNNING)
            throw IllegalStateException("Can only abort running build.")

        return serverInterface.abortBuild(build)
                .observeOn(GBRxSchedulers.main)
                .subscribeOn(GBRxSchedulers.network)
    }
}
