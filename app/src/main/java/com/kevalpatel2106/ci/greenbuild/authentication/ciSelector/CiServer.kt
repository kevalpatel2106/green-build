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

import android.support.annotation.DrawableRes

/**
 * Created by Kevalpatel2106 on 20-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class CiServer(

        /**
         * Resource of the logo of CI.
         */
        @DrawableRes
        val icon: Int,

        /**
         * Name of the ci server.
         */
        val name: String,

        /**
         * Sort description off the CI server.
         */
        val description: String,

        /**
         * Domain of the CI server. If the CI server has custom domain (enterprise edition) this
         * will be null.
         */
        val domain: String?,

        /**
         * Function to perform action when the ci server is clicked.
         */
        val onClick: () -> Unit
)
