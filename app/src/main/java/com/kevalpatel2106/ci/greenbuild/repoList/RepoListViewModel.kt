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

package com.kevalpatel2106.ci.greenbuild.repoList

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.grrenbuild.entities.Repo
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.RepoSortBy
import com.kevalpatel2106.greenbuild.utils.arch.BaseViewModel
import com.kevalpatel2106.greenbuild.utils.arch.SingleLiveEvent
import com.kevalpatel2106.greenbuild.utils.arch.recall
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class RepoListViewModel @Inject constructor(private val serverInterface: ServerInterface) : BaseViewModel() {

    internal val repoList = MutableLiveData<ArrayList<Repo>>()

    internal val errorLoadingList = SingleLiveEvent<String>()

    internal val hasNextPage = MutableLiveData<Boolean>()

    internal var isLoadingList = MutableLiveData<Boolean>()

    internal var isLoadingFirstTime = MutableLiveData<Boolean>()

    /**
     * [RepoSortBy] the indicates the sort order of the builds. By default the builds are last build
     * time in descending order.
     */
    internal var sortOrder: RepoSortBy = RepoSortBy.LAST_BUILD_TIME_DESC
        set(value) {
            field = value
            loadRepoList(1)
        }

    init {
        hasNextPage.value = true
        isLoadingList.value = false
        isLoadingFirstTime.value = false

        repoList.value = ArrayList()
        loadRepoList(1)
    }

    fun loadRepoList(page: Int) {
        serverInterface.getRepoList(page, sortOrder, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadingList.value = true

                    repoList.value?.let {
                        if (it.isEmpty()) isLoadingFirstTime.value = true
                    }
                }
                .doOnTerminate {
                    isLoadingList.value = false
                    isLoadingFirstTime.value = false
                }
                .map {
                    hasNextPage.value = it.hasNext

                    if (page == 1) repoList.value!!.clear()

                    repoList.value!!.addAll(it.list)
                    return@map repoList
                }
                .subscribe({
                    repoList.recall()
                }, {
                    errorLoadingList.value = it.message
                })
    }
}
