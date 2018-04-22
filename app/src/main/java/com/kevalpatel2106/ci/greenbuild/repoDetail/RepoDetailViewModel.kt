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

package com.kevalpatel2106.ci.greenbuild.repoDetail

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.buildList.BuildListFragment
import com.kevalpatel2106.ci.greenbuild.cacheList.CacheListFragment
import com.kevalpatel2106.ci.greenbuild.cronList.CronListFragment
import com.kevalpatel2106.ci.greenbuild.envVariableList.EnvVariableListFragment
import javax.inject.Inject

/**
 * Created by Keval on 22/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
internal class RepoDetailViewModel
@Inject internal constructor(private val application: BaseApplication) : BaseViewModel() {

    internal val selectedItem = MutableLiveData<Int>()

    internal var buildListFragment: BuildListFragment? = null
    internal var cacheListFragment: CacheListFragment? = null
    internal var envVarListFragment: EnvVariableListFragment? = null
    internal var cronListFragment: CronListFragment? = null

    init {
        selectedItem.value = 0
    }

    /**
     * Initialize all the fragments that are going to display into the viewpager.
     */
    fun initializeFragments(repoId: String) {
        if (buildListFragment == null) buildListFragment = BuildListFragment.get(repoId)
        if (cacheListFragment == null) cacheListFragment = CacheListFragment.get(repoId)
        if (cronListFragment == null) cronListFragment = CronListFragment.get(repoId)
        if (envVarListFragment == null) envVarListFragment = EnvVariableListFragment.get(repoId)
    }

    fun changeSelectedItem(int: Int) {
        selectedItem.value = int
    }

}