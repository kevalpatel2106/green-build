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
import com.kevalpatel2106.ci.greenbuild.about.AboutViewModel
import com.kevalpatel2106.ci.greenbuild.base.arch.DaggerViewModelFactory
import com.kevalpatel2106.ci.greenbuild.base.arch.ViewModelKey
import com.kevalpatel2106.ci.greenbuild.branchPicker.BranchPickerViewModel
import com.kevalpatel2106.ci.greenbuild.buildList.RecentBuildsFragment
import com.kevalpatel2106.ci.greenbuild.buildList.RecentBuildsListViewModel
import com.kevalpatel2106.ci.greenbuild.buildList.RepoBuildsListViewModel
import com.kevalpatel2106.ci.greenbuild.cacheList.CacheListViewModel
import com.kevalpatel2106.ci.greenbuild.ciSelector.CiSelectorViewModel
import com.kevalpatel2106.ci.greenbuild.cronList.CronListViewModel
import com.kevalpatel2106.ci.greenbuild.cronList.addCron.AddCronViewModel
import com.kevalpatel2106.ci.greenbuild.envVariableList.EnvVarsListViewModel
import com.kevalpatel2106.ci.greenbuild.envVariableList.editVariable.EditVariableViewModel
import com.kevalpatel2106.ci.greenbuild.main.MainActivityViewModel
import com.kevalpatel2106.ci.greenbuild.repoDetail.RepoDetailViewModel
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Created by Keval on 17/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    internal abstract fun bindRepoListViewModel(viewModel: RepoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoBuildsListViewModel::class)
    internal abstract fun bindBuildListViewModel(viewModelRepo: RepoBuildsListViewModel): ViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(CacheListViewModel::class)
    internal abstract fun bindCacheListViewModel(viewModel: CacheListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditVariableViewModel::class)
    internal abstract fun bindEditVariableViewModel(viewModel: EditVariableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CronListViewModel::class)
    internal abstract fun bindCronListViewModel(viewModel: CronListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCronViewModel::class)
    internal abstract fun bindAddCronViewModel(viewModel: AddCronViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BranchPickerViewModel::class)
    internal abstract fun bindBranchPickerViewModel(viewModel: BranchPickerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    internal abstract fun bindAboutViewModel(viewModel: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecentBuildsListViewModel::class)
    internal abstract fun bindRecentBuildsListViewModel(viewModel: RecentBuildsListViewModel): ViewModel
}
