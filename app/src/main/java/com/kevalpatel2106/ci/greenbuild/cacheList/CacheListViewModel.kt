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
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.arch.SingleLiveEvent
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.cache.Cache
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
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
                .subscribe({
                    hasModeData.value = it.hasNext

                    if (page == 1) cacheList.value!!.clear()

                    cacheList.value!!.addAll(it.list)
                    cacheList.value = cacheList.value
                }, {
                    errorLoadingList.value = it.message
                })
    }
}
