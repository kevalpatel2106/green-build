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

import com.kevalpatel2106.testutils.FileReader
import com.kevalpatel2106.testutils.MockServerManager
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection


/**
 * Created by Keval on 12/11/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

@RunWith(JUnit4::class)
class StringResponsesTests {
    private val mockServerManager = MockServerManager()
    private lateinit var mNetworkApi: NetworkApi

    @Before
    fun setUp() {
        mockServerManager.startMockWebServer()
        mNetworkApi = NetworkApi()
    }

    @After
    fun tearUp() {
        mockServerManager.close()
    }

    @Test
    @Throws(IOException::class)
    fun checkForTheStringSuccessResponse() {
        mockServerManager.enqueueResponse(File(mockServerManager.getResponsesPath() + "/success_sample.json"), "text/plain")

        val response = mNetworkApi.getRetrofitClient(mockServerManager.getBaseUrl())
                .create(TestApiService::class.java)
                .callBaseString()
                .execute()

        Assert.assertTrue(response.isSuccessful)
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_OK)
        Assert.assertEquals(response.body(), FileReader.getStringFromFile(File(mockServerManager.getResponsesPath() + "/success_sample.json")))
        Assert.assertEquals(response.message(), "OK")
    }

    @Test
    @Throws(IOException::class)
    fun checkForTheStringFailResponse() {
        mockServerManager.mockWebServer.enqueue(MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
                .setHeader("Content-Type", "text/html")
                .setBody(FileReader.getStringFromFile(File(mockServerManager.getResponsesPath() + "/success_sample.json"))))

        val response = mNetworkApi.getRetrofitClient(mockServerManager.getBaseUrl())
                .create(TestApiService::class.java)
                .callBaseString()
                .execute()

        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_FORBIDDEN)
    }
}
