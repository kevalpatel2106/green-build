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

package com.kevalpatel2106.ci.greenbuild.base.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by Keval on 12/11/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal interface TestApiService {

    @GET("test")
    fun callBase(): Call<TestData>


    @GET("test")
    fun callBaseString(): Call<String>

    @Headers("Add-Auth: true")
    @GET("test")
    fun callBaseWithAuthHeader(): Call<TestData>

    @GET("test")
    fun callBaseWithoutAuthHeader(): Call<TestData>

    @GET("test")
    fun callBaseWithoutCache(): Call<TestData>

    @Headers("Cache-Time: 5000")
    @GET("test")
    fun callBaseWithCache(): Call<TestData>
}
