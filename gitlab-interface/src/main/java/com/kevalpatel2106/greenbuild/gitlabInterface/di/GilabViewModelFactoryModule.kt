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

package com.kevalpatel2106.greenbuild.gitlabInterface.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.DaggerViewModelFactory
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.ViewModelKey
import com.kevalpatel2106.greenbuild.gitlabInterface.authentication.GitlabAuthenticationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Created by Keval on 17/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
internal abstract class GilabViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GitlabAuthenticationViewModel::class)
    internal abstract fun bindTravisAuthenticationViewModel(viewModel: GitlabAuthenticationViewModel): ViewModel
}
