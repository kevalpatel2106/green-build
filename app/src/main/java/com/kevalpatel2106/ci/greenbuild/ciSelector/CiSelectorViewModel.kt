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

package com.kevalpatel2106.ci.greenbuild.ciSelector

import android.content.Context
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 20-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class CiSelectorViewModel @Inject constructor(private val application: BaseApplication) : BaseViewModel() {

    fun getCiServerList(): ArrayList<CiServer> {
        val ciServers = ArrayList<CiServer>()

        ciServers.add(CiServer("Travis CI", "https://travis-ci.org"))
        ciServers.add(CiServer("Travis CI", "https://travis-ci.com"))
        ciServers.add(CiServer("Travis CI", null))

        return ciServers
    }

}
