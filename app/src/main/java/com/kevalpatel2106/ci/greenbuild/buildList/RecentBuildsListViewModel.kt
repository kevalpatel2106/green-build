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

package com.kevalpatel2106.ci.greenbuild.buildList

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.CheckResult
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Build
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.BuildSortBy
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.BuildState
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.SingleLiveEvent
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.recall
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Keval on 18/04/18.
 *
 * @constructor Dagger injectable public constructor.
 * @param serverInterface [ServerInterface] for loading the list of recent [Build] and restarting/aborting
 * the build.
 * @param compatibilityCheck [CompatibilityCheck] to check the compatibility with the CI platform.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class RecentBuildsListViewModel @Inject constructor(
        private val serverInterface: ServerInterface,
        private val compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {

    /**
     * [Disposable] for the polling [Observable]. If this [Disposable] is disposed or null, application
     * is not polling for the changes.
     */
    private var pollingDisposable: Disposable? = null

    /**
     * [MutableLiveData] of the [ArrayList] of recent [Build]. UI can observe this to check any changes.
     */
    internal val buildsList = MutableLiveData<ArrayList<Build>>()

    /**
     * [SingleLiveEvent] that emits the error message occurred while loading.
     *
     * @see loadRecentBuildsList
     */
    internal val errorLoadingList = SingleLiveEvent<String>()

    /**
     * [MutableLiveData] that sets vale true if the builds are loading else false.
     *
     * @see loadRecentBuildsList
     */
    internal var isLoadingList = MutableLiveData<Boolean>()

    /**
     * [MutableLiveData] that sets vale true if [loadRecentBuildsList] is loading first page else false.
     *
     * @see loadRecentBuildsList
     */
    internal var isLoadingFirstTime = MutableLiveData<Boolean>()

    /**
     * [MutableLiveData] that sets vale true if there are no more [Build] to load.
     *
     * @see loadRecentBuildsList
     */
    internal var hasModeData = MutableLiveData<Boolean>()

    /**
     * [MutableLiveData] that notifies when [restartBuild], restarts the [Build] successfully.
     *
     * @see restartBuild
     */
    internal var buildRestartComplete = MutableLiveData<Unit>()

    /**
     * [SingleLiveEvent] that emits the error message occurred while restarting the [Build].
     *
     * @see restartBuild
     */
    internal var errorRestartingBuild = SingleLiveEvent<String>()

    /**
     * [MutableLiveData] that notifies when [abortBuild], aborts the [Build] successfully.
     *
     * @see abortBuild
     */
    internal var buildAbortComplete = MutableLiveData<Unit>()

    /**
     * [SingleLiveEvent] that emits the error message occurred while aborting the [Build].
     *
     * @see abortBuild
     */
    internal var errorAbortingBuild = SingleLiveEvent<String>()

    init {
        if (!compatibilityCheck.isRecentBuildsListSupported())
            throw IllegalStateException("Recent builds listing by repository is not supported for this CI.")

        hasModeData.value = true
        isLoadingList.value = false
        isLoadingFirstTime.value = false

        buildsList.value = ArrayList()

        //Load the first page.
        loadRecentBuildsList(1)

//        // Start polling
//        pollingDisposable = startPolling()
    }

    @CheckResult
    private fun startPolling(): Disposable {
        return Observable.interval(5, 10, TimeUnit.SECONDS)
                .timeInterval()
                .flatMap { serverInterface.getRecentBuildsList(1, BuildSortBy.FINISHED_AT_DESC) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable { it.list }
                .map {
                    val newBuild = it
                    buildsList.value?.let {
                        it.filter {
                            it.id == newBuild.id
                        }.forEach {
                            //Fill with the new values.
                            it.finishedAt = newBuild.finishedAt
                            it.startedAt = newBuild.startedAt
                            it.state = newBuild.state
                            it.previousState = newBuild.previousState
                        }
                    }
                    return@map
                }
                .subscribe({
                    Timber.e("Changes done.")
                    buildsList.recall()
                }, {
                    errorLoadingList.value = it.message
                })
    }

    /**
     * Load the list of the recent [Build] from the server for the [page].
     */
    fun loadRecentBuildsList(page: Int) {
        serverInterface.getRecentBuildsList(page, BuildSortBy.FINISHED_AT_DESC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadingList.value = true
                    buildsList.value?.let { if (it.isEmpty()) isLoadingFirstTime.value = true }
                }
                .doOnTerminate {
                    isLoadingList.value = false
                    isLoadingFirstTime.value = false
                }
                .map {
                    hasModeData.value = it.hasNext

                    if (page == 1) buildsList.value!!.clear()
                    buildsList.value!!.addAll(it.list)

                    return@map buildsList.value
                }
                .subscribe({
                    buildsList.recall()
                }, {
                    errorLoadingList.value = it.message
                })
    }

    /**
     * Restart the [build] if the build is not running.
     *
     * @throws IllegalStateException If the [build] state is [BuildState.BOOTING] or [BuildState.RUNNING].
     * @throws IllegalStateException If the restart build is not supported by the CI provider. (i.e.
     * [CompatibilityCheck.isRestartBuildSupported] is false.)
     */
    fun restartBuild(build: Build) {
        if (!compatibilityCheck.isRestartBuildSupported())
            throw IllegalStateException("Restart build is not supported for this CI platform.")

        if (build.state == BuildState.RUNNING || build.state == BuildState.BOOTING)
            throw IllegalStateException("Cannot restart running or booting build")

        serverInterface.restartBuild(build)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    build.isRestarting = true
                    buildsList.recall()
                }
                .doOnTerminate {
                    build.isRestarting = false
                    buildsList.recall()
                }
                .subscribe({
                    buildRestartComplete.value = Unit

                    //Refresh the page
                    addDisposable(Observable.timer(1200, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { loadRecentBuildsList(1) })
                }, {
                    errorRestartingBuild.value = it.message
                })
    }

    /**
     * Abort the [build] if the build is running.
     *
     * @throws IllegalStateException If the [build] state is not [BuildState.BOOTING] or [BuildState.RUNNING].
     * @throws IllegalStateException If the abort build is not supported by the CI provider. (i.e.
     * [CompatibilityCheck.isAbortBuildSupported] is false.)
     */
    fun abortBuild(build: Build) {
        if (!compatibilityCheck.isAbortBuildSupported())
            throw IllegalStateException("Abort build is not supported for this CI platform.")

        if (build.state != BuildState.RUNNING)
            throw IllegalStateException("Can only abort running build.")

        serverInterface.abortBuild(build)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    build.isAborting = true
                    buildsList.recall()
                }
                .doOnTerminate {
                    build.isAborting = false
                    buildsList.recall()
                }
                .subscribe({
                    buildAbortComplete.value = Unit

                    //Refresh the page
                    addDisposable(Observable.timer(1200, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { loadRecentBuildsList(1) })
                }, {
                    errorAbortingBuild.value = it.message
                })
    }
}
