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

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

/**
 * Created by Kevalpatel2106 on 30-Nov-17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@RunWith(JUnit4::class)
class RetrofitClientTest {

    @Test
    @Throws(IOException::class)
    fun checkBaseUrl() {
        val retrofit = NetworkApi().getRetrofitClient("http://google.com")
        Assert.assertEquals(retrofit.baseUrl().toString(), "http://google.com/")
    }

    @Test
    @Throws(IOException::class)
    fun checkGsonAdapter() {
        val retrofit = NetworkApi().getRetrofitClient("http://google.com")
        Assert.assertEquals(retrofit.converterFactories().size, 2)  //This should be custom converter
    }
}
