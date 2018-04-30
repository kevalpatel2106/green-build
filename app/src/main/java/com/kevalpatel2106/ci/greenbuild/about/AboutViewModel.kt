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

package com.kevalpatel2106.ci.greenbuild.about

import android.arch.lifecycle.MutableLiveData
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.utils.openLink
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import javax.inject.Inject


/**
 * Created by Keval on 18/12/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class AboutViewModel @Inject constructor(private val application: BaseApplication) : BaseViewModel() {

    internal val isCheckingUpdate = MutableLiveData<Boolean>()

    init {
        isCheckingUpdate.value = false
    }

    fun handleRateUs() {
        try {
            val uri = Uri.parse("market://details?id=" + application.packageName)

            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            application.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            application.openLink(application.getString(R.string.rate_app_url))
        }
    }

    fun handleOpenSourceLibs(context: Context) {
        LibsBuilder().withActivityStyle(Libs.ActivityStyle.DARK)
                .withActivityTitle("We  ♡  Open source")
                .withAutoDetect(true)
                .withAboutIconShown(true)
                .withAboutVersionShownName(true)
                .withAboutVersionShownCode(false)
                .withLicenseShown(true)
                .start(context)
    }

}
