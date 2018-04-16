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

package com.kevalpatel2106.testutils

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.nio.file.Paths

/**
 * Created by Keval on 05/12/17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class MockServerManager : Closeable {

    lateinit var mockWebServer: MockWebServer

    @SuppressLint("NewApi")
    fun getResponsesPath(): String {

        if (System.getenv("CI") == "true") {
            return "/bitrise/src/test-utils/src/main/res/raw"
        }

        @Suppress("UselessCallOnNotNull")
        val rootDirPath = Paths.get("").toAbsolutePath().toString().let {
            if (it.isNullOrEmpty()) {
                System.getenv("PROJECT_ROOT")
            } else {
                it.substring(0 until it.lastIndexOf("Stand-Up"))
                        .plus("Stand-Up")
            }
        }

        return "$rootDirPath/test-utils/src/main/res/raw"
    }

    /**
     * Start mock web server for the wikipedia api.
     *
     * @return [MockWebServer]
     */
    fun startMockWebServer(): MockWebServer {
        try {
            mockWebServer = MockWebServer()
            mockWebServer.start()
            return mockWebServer
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Failed to start mock server.")
        }

    }

    /**
     * Enqueue the next response in [mockWebServer].
     */
    @JvmOverloads
    fun enqueueResponse(response: String, type: String = "application/json") {
        mockWebServer.enqueue(MockResponse()
                .setHeader("Content-type", type)
                .setBody(response)
                .setResponseCode(HttpURLConnection.HTTP_OK))
    }

    /**
     * Enqueue the next response in [mockWebServer].
     */
    @JvmOverloads
    fun enqueueResponse(response: File, type: String = "application/json") {
        enqueueResponse(response = FileReader.getStringFromFile(file = response), type = type)
    }

    /**
     * Enqueue the next response in [mockWebServer].
     */
    @JvmOverloads
    fun enqueueResponse(context: Context, rawFile: Int, type: String = "application/json") {
        enqueueResponse(response = FileReader.getStringFromRawFile(context, rawFile), type = type)
    }


    fun getBaseUrl() = mockWebServer.url("/").toString()

    override fun close() {
        mockWebServer.shutdown()
    }
}
