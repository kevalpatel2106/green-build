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

package com.kevalpatel2106.ci.greenbuild.cronList.addCron

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import javax.inject.Inject

/**
 * Created by Kevalpatel2106 on 24-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class AddCronViewModel @Inject constructor(
        private val application: BaseApplication,
        compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {

    internal val isSchedulingCron = MutableLiveData<Boolean>()

    init {
        if (!compatibilityCheck.isAddCronJobsSupported())
            throw IllegalStateException("This CI provider doesn't support for scheduling new cron.")

        isSchedulingCron.value = false
    }

    internal fun scheduleCron() {

    }

}
