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

package com.kevalpatel2106.ci.greenbuild.authentication.ciSelector

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.VisibleForTesting
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.authentication.travis.TravisAuthenticationActivity
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.greenbuild.travisInterface.TravisServerInterface
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 20-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class CiSelectorViewModel @Inject constructor(private val application: BaseApplication) : BaseViewModel() {

    internal val ciServers = MutableLiveData<ArrayList<CiServer>>()

    init {
        ciServers.value = ArrayList()
        loadCiServerList()
    }

    @VisibleForTesting
    internal fun loadCiServerList() {
        ciServers.value?.let {
            it.clear()

            it.add(CiServer(
                    icon = R.drawable.logo_travis_ci_org,
                    name = "Travis CI (Open Source repo)",
                    description = "Travis continuous integration for open source projects on GitHub.",
                    domain = "https://travis-ci.org",
                    onClick = {
                        TravisAuthenticationActivity.launch(application, TravisServerInterface.TRAVIS_CI_ORG)
                    }))
            it.add(CiServer(
                    icon = R.drawable.logo_travis_ci_com,
                    name = "Travis CI (Private repo)",
                    description = "Travis continuous integration for private repositories on GitHub.",
                    domain = "https://travis-ci.com",
                    onClick = {
                        TravisAuthenticationActivity.launch(application, TravisServerInterface.TRAVIS_CI_COM)
                    }))
            it.add(CiServer(
                    icon = R.drawable.logo_travis_ci_enterprice,
                    name = "Travis CI (Enterprise)",
                    description = "Self hosted continuous integration from Travis CI.",
                    domain = null,
                    onClick = {
                        TravisAuthenticationActivity.launch(application, null)
                    }))

            //Notify
            ciServers.value = ciServers.value
        }
    }

}
