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

package com.kevalpatel2106.ci.greenbuild.di

import com.kevalpatel2106.ci.greenbuild.about.AboutActivity
import com.kevalpatel2106.ci.greenbuild.base.application.ApplicationComponent
import com.kevalpatel2106.ci.greenbuild.branchPicker.BranchPickerDialog
import com.kevalpatel2106.ci.greenbuild.buildList.RecentBuildsFragment
import com.kevalpatel2106.ci.greenbuild.buildList.RepoBuildsListFragment
import com.kevalpatel2106.ci.greenbuild.cacheList.CacheListFragment
import com.kevalpatel2106.ci.greenbuild.ciSelector.CiSelectorActivity
import com.kevalpatel2106.ci.greenbuild.cronList.CronListFragment
import com.kevalpatel2106.ci.greenbuild.cronList.addCron.AddCronActivity
import com.kevalpatel2106.ci.greenbuild.envVariableList.EnvVariableListFragment
import com.kevalpatel2106.ci.greenbuild.envVariableList.editVariable.EditVariableDialog
import com.kevalpatel2106.ci.greenbuild.main.MainActivity
import com.kevalpatel2106.ci.greenbuild.repoDetail.RepoDetailActivity
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListFragment
import com.kevalpatel2106.ci.greenbuild.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Kevalpatel2106 on 17-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Singleton
@Component(dependencies = [ApplicationComponent::class], modules = [DiModule::class])
interface DiComponent {

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [SplashActivity].
     */
    fun inject(splashActivity: SplashActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [RepoListFragment].
     */
    fun inject(repoListFragment: RepoListFragment)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [RepoBuildsListFragment].
     */
    fun inject(buildsListFragment: RepoBuildsListFragment)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [CiSelectorActivity].
     */
    fun inject(ciSelectorActivity: CiSelectorActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [RepoDetailActivity].
     */
    fun inject(repoDetailActivity: RepoDetailActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [EnvVariableListFragment].
     */
    fun inject(envVariableListFragment: EnvVariableListFragment)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [CacheListFragment].
     */
    fun inject(cacheListFragment: CacheListFragment)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [EditVariableDialog].
     */
    fun inject(editVariableDialog: EditVariableDialog)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [CronListFragment].
     */
    fun inject(cronListFragment: CronListFragment)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [AddCronActivity].
     */
    fun inject(addCronActivity: AddCronActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [BranchPickerDialog].
     */
    fun inject(branchPickerDialog: BranchPickerDialog)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [MainActivity].
     */
    fun inject(mainActivity: MainActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [AboutActivity].
     */
    fun inject(aboutActivity: AboutActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [RecentBuildsFragment].
     */
    fun inject(recentBuildsFragment: RecentBuildsFragment)
}
