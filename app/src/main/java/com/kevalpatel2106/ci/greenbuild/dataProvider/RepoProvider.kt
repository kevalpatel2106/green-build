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

package com.kevalpatel2106.ci.greenbuild.dataProvider

import com.kevalpatel2106.ci.greenbuild.base.ciInterface.*
import com.kevalpatel2106.grrenbuild.entities.Build
import com.kevalpatel2106.grrenbuild.entities.BuildState
import com.kevalpatel2106.grrenbuild.entities.Repo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 04-May-18.
 * A class that provides [Repo] data to the view models and acts as a repository for the [Repo]. This
 * class will responsible for connecting with CI platform servers and database to perform operations
 * with [Repo].
 *
 * @constructor Dagger injectable public constructor.
 * @param serverInterface [ServerInterface] for making the api calls to the CI servers.
 * @param compatibilityCheck [CompatibilityCheck] to check the compatibility with the CI platform.
 * @param GBRxSchedulers [GBRxSchedulers] to provide threads.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class RepoProvider @Inject constructor(
        private val serverInterface: ServerInterface,
        private val compatibilityCheck: CompatibilityCheck,
        private val GBRxSchedulers: GBRxSchedulers
) {

}
