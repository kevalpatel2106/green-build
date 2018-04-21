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

import com.kevalpatel2106.ci.greenbuild.authentication.ciSelector.CiSelectorActivity
import com.kevalpatel2106.ci.greenbuild.authentication.travis.TravisAuthenticationActivity
import com.kevalpatel2106.ci.greenbuild.base.application.ApplicationComponent
import com.kevalpatel2106.ci.greenbuild.buildList.BuildListActivity
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListActivity
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
     * Inject the [DiModule] and [ApplicationComponent] in [TravisAuthenticationActivity].
     */
    fun inject(travisAuthenticationActivity: TravisAuthenticationActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [RepoListActivity].
     */
    fun inject(repoListActivity: RepoListActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [BuildListActivity].
     */
    fun inject(buildsListActivity: BuildListActivity)

    /**
     * Inject the [DiModule] and [ApplicationComponent] in [CiSelectorActivity].
     */
    fun inject(ciSelectorActivity: CiSelectorActivity)
}
