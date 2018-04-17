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

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by Keval on 17/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
internal interface TravisEndpoints {

    /**
     * Get the information of the current user. It is required to send the auth header with this
     * endpoint.
     *
     * @see <a href="https://developer.travis-ci.org/explore/user">API Explorer</a>
     */
    @GET("user")
    @Headers("Add-Auth: true")
    fun getMyProfile(): Observable<TravisUser>
}
