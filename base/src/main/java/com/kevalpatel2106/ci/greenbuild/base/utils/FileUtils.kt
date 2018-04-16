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

package com.kevalpatel2106.utils

import android.content.Context

import java.io.File

/**
 * Created by Keval on 20-Dec-16.
 * Utility functions related to files and storage.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

object FileUtils {

    /**
     * Get the cache directory for the application. If external cache directory is not available,
     * it will return internal (data/data) cache directory.
     *
     * @param context Instance of the caller.
     */
    @JvmStatic
    fun getCacheDir(context: Context): File = context.externalCacheDir ?: context.cacheDir
}
