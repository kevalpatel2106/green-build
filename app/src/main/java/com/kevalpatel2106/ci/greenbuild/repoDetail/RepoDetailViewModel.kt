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
import android.support.v4.app.Fragment
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.buildList.RepoBuildsListFragment
import com.kevalpatel2106.ci.greenbuild.cacheList.CacheListFragment
import com.kevalpatel2106.ci.greenbuild.cronList.CronListFragment
import com.kevalpatel2106.ci.greenbuild.envVariableList.EnvVariableListFragment
import com.kevalpatel2106.greenbuild.utils.arch.BaseViewModel
import com.kevalpatel2106.grrenbuild.entities.Repo
import javax.inject.Inject

/**
 * Created by Keval on 22/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class RepoDetailViewModel @Inject internal constructor(
        private val compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {

    internal val selectedItem = MutableLiveData<Int>()
    internal val isDisplayFab = MutableLiveData<Boolean>()
    internal val repo = MutableLiveData<Repo>()

    // Compatibility
    internal val isBuildListCompatible = compatibilityCheck.isBuildsListByRepoSupported()
    internal val isCacheListCompatible = compatibilityCheck.isCacheListListSupported()
    internal val isCronListCompatible = compatibilityCheck.isCronJobsListSupported()
    internal val isEnvVarsListCompatible = compatibilityCheck.isEnvironmentVariableListSupported()

    private val fragmentsList = ArrayList<Fragment>()
    private var repoBuildsListFragment: RepoBuildsListFragment? = null
    private var cacheListFragment: CacheListFragment? = null
    private var envVarListFragment: EnvVariableListFragment? = null
    private var cronListFragment: CronListFragment? = null


    init {
        if (!isCacheListCompatible && !isBuildListCompatible && !isCronListCompatible && !isEnvVarsListCompatible) {
            throw IllegalStateException("This CI provider doesn't support any features at all!!!")
        }
        selectedItem.value = 0
        isDisplayFab.value = false
        repo.value = null
    }

    /**
     * Initialize all the fragments that are going to display into the viewpager. This should be called
     * whenever the [RepoDetailActivity] is created.
     *
     */
    fun initializeFragments(repoId: String) {
        //DON'T CHANGE THE ORDER OF THE FRAGMENTS IN ARRAY LIST.
        //This order should be in sync with the bottom navigation list menu.

        if (repoBuildsListFragment == null && isBuildListCompatible) {
            repoBuildsListFragment = RepoBuildsListFragment.get(repoId)
            fragmentsList.add(repoBuildsListFragment!!)
        }
        if (envVarListFragment == null && isEnvVarsListCompatible) {
            envVarListFragment = EnvVariableListFragment.get(repoId)
            fragmentsList.add(envVarListFragment!!)
        }
        if (cronListFragment == null && isCronListCompatible) {
            cronListFragment = CronListFragment.get(repoId)
            fragmentsList.add(cronListFragment!!)
        }
        if (cacheListFragment == null && isCacheListCompatible) {
            cacheListFragment = CacheListFragment.get(repoId)
            fragmentsList.add(cacheListFragment!!)
        }
    }

    /**
     * Get the count of the [Fragment] to be displayed in the viewpager of the [RepoDetailActivity].
     * This count must be same with the bottom navigation items that are enabled in [RepoDetailActivity].
     */
    internal fun getViewPagerCount(): Int {
        return fragmentsList.size
    }

    /**
     * Get the [Fragment] to display in the view pager of [RepoDetailActivity].
     *
     * @see RepoDetailPagerAdapter
     */
    internal fun getFragmentByPosition(position: Int): Fragment {
        if (fragmentsList.size > position) {
            return fragmentsList[position]
        } else {
            throw IllegalStateException("Invalid bottom navigation item position $position")
        }
    }

    /**
     * Change the currently selected item position. The activity can observe [selectedItem] to
     * get notify when the selected item change.
     */
    fun changeSelectedItem(itemId: Int) {
        selectedItem.value = when (itemId) {
            R.id.repo_detail_menu_bottom_build -> {
                isDisplayFab.value = false
                fragmentsList.indexOf(repoBuildsListFragment as Fragment)
            }
            R.id.repo_detail_menu_bottom_env_var -> {
                isDisplayFab.value = compatibilityCheck.isAddEnvironmentVariableSupported()
                        && repo.value?.permissions?.canCreateEnvVar ?: true /* Check the permission */
                fragmentsList.indexOf(envVarListFragment as Fragment)
            }
            R.id.repo_detail_menu_bottom_cron -> {
                isDisplayFab.value = compatibilityCheck.isAddCronJobsSupported()
                        && repo.value?.permissions?.canScheduleCron ?: true /* Check the permission */
                fragmentsList.indexOf(cronListFragment as Fragment)
            }
            R.id.repo_detail_menu_bottom_cache -> {
                isDisplayFab.value = false
                fragmentsList.indexOf(cacheListFragment as Fragment)
            }
            else -> throw IllegalArgumentException("Invalid item id : $itemId")
        }

        if (selectedItem.value!! < -1) throw IllegalStateException("Cannot find the fragment for $itemId.")
    }

    fun fabClicked() {
        when {
            selectedItem.value == fragmentsList.indexOf(cronListFragment as Fragment) -> {
                //Add new cron
                cronListFragment?.addCron()
            }
            selectedItem.value == fragmentsList.indexOf(envVarListFragment as Fragment) -> {
                //Add new environment variable
                TODO("Add environment variable.")
            }
        }
    }
}
