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

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kevalpatel2106.ci.greenbuild.authentication.ciSelector.CiSelectorViewModel
import com.kevalpatel2106.ci.greenbuild.authentication.travis.TravisAuthenticationViewModel
import com.kevalpatel2106.ci.greenbuild.base.arch.DaggerViewModelFactory
import com.kevalpatel2106.ci.greenbuild.base.arch.ViewModelKey
import com.kevalpatel2106.ci.greenbuild.buildList.BuildsListViewModel
import com.kevalpatel2106.ci.greenbuild.envVariableList.EnvVarsListViewModel
import com.kevalpatel2106.ci.greenbuild.repoDetail.RepoDetailViewModel
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Created by Keval on 17/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TravisAuthenticationViewModel::class)
    internal abstract fun bindTravisAuthenticationViewModel(viewModel: TravisAuthenticationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    internal abstract fun bindRepoListViewModel(viewModel: RepoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BuildsListViewModel::class)
    internal abstract fun bindBuildListViewModel(viewModel: BuildsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CiSelectorViewModel::class)
    internal abstract fun bindCiSelectorViewModel(viewModel: CiSelectorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailViewModel::class)
    internal abstract fun bindRepoDetailViewModel(viewModel: RepoDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EnvVarsListViewModel::class)
    internal abstract fun bindEnvVarsListViewModel(viewModel: EnvVarsListViewModel): ViewModel
}
