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

package com.kevalpatel2106.grrenbuild.entities

import android.support.annotation.DrawableRes
import android.support.v4.app.ActivityOptionsCompat

/**
 * Created by Kevalpatel2106 on 20-Apr-18.
 * Kotlin bin for CI server. A CI platform can have multiple [CiServer]. (e.g. Gitlab CI platform
 * supports two type off the CI servers. 1) Gitlab.com CI and 2) Gitlab Enterprise CI.) This bin will
 * hold the information related to the CI server configurations (such as [domain], [icon] of server).
 *
 * @constructor Public constructor.
 * @param icon Resource of the logo of CI. This can be favicon of the CI server.
 * @param name Name of the ci server.
 * @param description Sort description of the CI server.
 * @param domain Domain of the CI server. If the CI server has custom domain (enterprise edition), this
 * will be null.
 * @param onClick Function to perform action when the ci server is clicked.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class CiServer(
        @DrawableRes
        val icon: Int,
        val name: String,
        val description: String,
        val domain: String?,
        val onClick: (options: ActivityOptionsCompat?) -> Unit
)
