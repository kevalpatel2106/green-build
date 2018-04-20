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

package com.kevalpatel2106.greenbuild.travisInterface

/**
 * Created by Keval on 20/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
internal class Constants{

    companion object {

        // name of build states
        const val CANCEL_BUILD = "canceled"
        const val PASSED_BUILD = "passed"
        const val RUNNING_BUILD = "started"
        const val BOOTING_BUILD = "created"
        const val FAILED_BUILD = "failed"
        const val ERRORED_BUILD= "errored"

        // name of the events
        const val PUSH_EVENT = "push"
        const val PULL_REQUEST_EVENT = "pull_request"
    }


}
