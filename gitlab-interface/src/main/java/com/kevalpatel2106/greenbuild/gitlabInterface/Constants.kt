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

package com.kevalpatel2106.greenbuild.gitlabInterface

/**
 * Created by Keval on 20/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class Constants {

    companion object {

        /**
         * Base url for accessing the API for travis-ci.org.
         *
         * @see <a href="https://developer.travis-ci.org/gettingstarted">API Doc</a>
         */
        internal const val GITLAB_API = "https://api.gitlab.com/api/v4"

        // name of build states
        internal const val CANCEL_BUILD = "canceled"
        internal const val PASSED_BUILD = "passed"
        internal const val RUNNING_BUILD = "started"
        internal const val BOOTING_BUILD = "created"
        internal const val FAILED_BUILD = "failed"
        internal const val ERRORED_BUILD = "errored"

        // name of trigger events
        internal const val PUSH_EVENT = "push"
        internal const val PULL_REQUEST_EVENT = "pull_request"
        internal const val CRON_EVENT = "cron"
        internal const val API_EVENT = "api"
    }


}
