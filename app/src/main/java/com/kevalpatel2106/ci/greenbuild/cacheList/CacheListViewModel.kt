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

package com.kevalpatel2106.ci.greenbuild.cacheList

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.grrenbuild.entities.Cache
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
internal class CacheListViewModel @Inject constructor(
        private val serverInterface: ServerInterface,
        compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {
    internal val isDeleteVariableSupported = compatibilityCheck.isCacheDeleteSupported()

    internal val cacheList = MutableLiveData<ArrayList<Cache>>()

    internal val errorLoadingList = SingleLiveEvent<String>()

    internal var isLoadingList = MutableLiveData<Boolean>()

    internal var isLoadingFirstTime = MutableLiveData<Boolean>()

    internal var hasModeData = MutableLiveData<Boolean>()

    internal var errorDeletingCache = MutableLiveData<String>()

    init {
        if (!compatibilityCheck.isCacheListListSupported())
            throw IllegalStateException("Cache listing by repository is not supported for this CI.")

        hasModeData.value = true
        isLoadingList.value = false
        isLoadingFirstTime.value = false

        cacheList.value = ArrayList()
    }

    fun loadCacheList(repoId: String, page: Int) {
        serverInterface.getCachesList(page, repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadingList.value = true

                    cacheList.value?.let {
                        if (it.isEmpty()) isLoadingFirstTime.value = true
                    }
                }
                .doOnTerminate {
                    isLoadingList.value = false
                    isLoadingFirstTime.value = false
                }
                .map {
                    hasModeData.value = it.hasNext

                    if (page == 1) cacheList.value!!.clear()

                    cacheList.value!!.addAll(it.list)
                    return@map cacheList
                }
                .subscribe({
                    cacheList.recall()
                }, {
                    errorLoadingList.value = it.message
                })
    }

    /**
     * Delete [cache] from the server. This method will display the confirmation dialog and if user
     * confirms delete operation it will delete [cache] by calling [ServerInterface.deleteCache].
     *
     * @see ServerInterface.deleteCache
     */
    internal fun deleteCache(cache: Cache) {
        serverInterface.deleteCache(cache.repositoryId, cache.branchName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    cache.isDeleting = true
                    cacheList.recall()
                }
                .doOnTerminate {
                    cache.isDeleting = false
                    cacheList.recall()
                }
                .subscribe({
                    cacheList.value!!.remove(cache)
                }, {
                    errorDeletingCache.value = it.message
                })
    }
}
