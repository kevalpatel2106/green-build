/*
 *  Copyright 2018 Keval Patel.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.kevalpatel2106.ci.greenbuild.base.network

import com.kevalpatel2106.testutils.MockServerManager
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.net.HttpURLConnection


/**
 * Created by Keval on 12/11/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

@RunWith(JUnit4::class)
class ServerErrorCodeTests {

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
    fun checkPageNotFound() {
        //404 response
        mockServerManager.mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND))

        val response = mNetworkApi.getRetrofitClient(mockServerManager.getBaseUrl())
                .create(TestApiService::class.java)
                .callBase()
                .execute()
        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_NOT_FOUND)
        Assert.assertEquals(response.message(), NetworkConfig.ERROR_MESSAGE_NOT_FOUND)
    }

    @Test
    @Throws(IOException::class)
    fun checkUnAuthorise() {
        //404 response
        mockServerManager.mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED))

        val response = mNetworkApi.getRetrofitClient(mockServerManager.getBaseUrl())
                .create(TestApiService::class.java)
                .callBase()
                .execute()

        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_UNAUTHORIZED)
    }

    @Test
    @Throws(IOException::class)
    fun checkBadRequest() {
        //400 response
        mockServerManager.mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST))

        val response = mNetworkApi.getRetrofitClient(mockServerManager.getBaseUrl())
                .create(TestApiService::class.java)
                .callBase()
                .execute()

        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_BAD_REQUEST)
        Assert.assertEquals(response.message(), NetworkConfig.ERROR_MESSAGE_BAD_REQUEST)
    }

    @Suppress("DEPRECATION")
    @Test
    @Throws(IOException::class)
    fun checkServerBusy() {
        //500 response
        mockServerManager.mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_SERVER_ERROR))

        val response = mNetworkApi.getRetrofitClient(mockServerManager.getBaseUrl())
                .create(TestApiService::class.java)
                .callBase()
                .execute()

        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_SERVER_ERROR)
        Assert.assertEquals(response.message(), NetworkConfig.ERROR_MESSAGE_SERVER_BUSY)
    }

    @Test
    @Throws(IOException::class)
    fun checkUnknownResponseCode() {
        mockServerManager.mockWebServer.enqueue(MockResponse().setResponseCode(103))

        val response = mNetworkApi.getRetrofitClient(mockServerManager.getBaseUrl())
                .create(TestApiService::class.java)
                .callBase()
                .execute()

        Assert.assertFalse(response.isSuccessful)
        Assert.assertEquals(response.code(), 103)
        Assert.assertEquals(response.message(), NetworkConfig.ERROR_MESSAGE_SOMETHING_WRONG)
    }
}
