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
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.github.javiersantos.appupdater.objects.Update
import com.kevalpatel2106.ci.greenbuild.BuildConfig
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.BaseViewModel
import javax.inject.Inject


/**
 * Created by Keval on 18/12/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class AboutViewModel @Inject constructor(private val application: BaseApplication) : BaseViewModel() {

    internal val isCheckingUpdate = MutableLiveData<Boolean>()
    internal val latestVersion = MutableLiveData<Update>()
    internal val isUpdateAvailable = MutableLiveData<Boolean>()

    init {
        isCheckingUpdate.value = false
        isUpdateAvailable.value = false
        latestVersion.value = Update(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    fun checkForUpdates() {
        isCheckingUpdate.value = true
        AppUpdaterUtils(application)
                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                .setUpdateFrom(UpdateFrom.GITHUB)
                .setGitHubUserAndRepo(
                        application.getString(R.string.github_username),
                        application.getString(R.string.github_project_name)
                )
                .withListener(object : AppUpdaterUtils.UpdateListener {
                    override fun onSuccess(update: Update, updateAvailable: Boolean) {
                        isCheckingUpdate.value = false
                        isUpdateAvailable.value = !updateAvailable
                        latestVersion.value = update
                    }

                    override fun onFailed(p0: AppUpdaterError?) {
                        isCheckingUpdate.value = false
                        isUpdateAvailable.value = false
                        latestVersion.value = Update(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
                    }
                })
                .start()
    }
}
