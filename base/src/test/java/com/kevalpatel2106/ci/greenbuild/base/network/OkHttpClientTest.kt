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
class OkHttpClientTest {

    @Test
    @Throws(IOException::class)
    fun checkOkHttpClient() {
        val okHttpClient = NetworkApi().prepareOkHttpClient()
        Assert.assertEquals(okHttpClient.readTimeoutMillis().toLong(), NetworkConfig.READ_TIMEOUT * 60 * 1000)
        Assert.assertEquals(okHttpClient.writeTimeoutMillis().toLong(), NetworkConfig.WRITE_TIMEOUT * 60 * 1000)
        Assert.assertEquals(okHttpClient.connectTimeoutMillis().toLong(), NetworkConfig.CONNECTION_TIMEOUT * 60 * 1000)
    }
}
