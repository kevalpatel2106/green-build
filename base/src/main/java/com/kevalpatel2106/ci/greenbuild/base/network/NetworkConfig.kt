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

/**
 * Created by Keval on 17/11/17.
 * This class holds network config.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object NetworkConfig {
    //Time out config.
    internal const val READ_TIMEOUT = 1L
    internal const val WRITE_TIMEOUT = 1L
    internal const val CONNECTION_TIMEOUT = 1L

    //Error messages.
    const val ALL_OK = "Success!!!"
    const val ERROR_MESSAGE_INTERNET_NOT_AVAILABLE = "Internet is not available. Please try again."
    const val ERROR_MESSAGE_SOMETHING_WRONG = "Something went wrong."
    const val ERROR_MESSAGE_BAD_REQUEST = "Invalid request. Please try again."
    const val ERROR_MESSAGE_NOT_FOUND = "Cannot perform the request. Please try again."
    const val ERROR_MESSAGE_SERVER_BUSY = "Server is too busy at this moment. Please try again."
}
