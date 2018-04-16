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

package com.kevalpatel2106.ci.greenbuild.base.utils

import android.content.Context
import com.kevalpatel2106.utils.FileUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.io.File


/**
 * Created by Keval on 11/10/17.

 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class FileUtilsTest {

    @Test
    fun checkGetCacheDirWithInternalCache() {
        val context = Mockito.mock(Context::class.java)
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(file.absolutePath).thenReturn("test_path")
        Mockito.`when`(context.cacheDir).thenReturn(file)

        //Check if the output is not null.
        assertNotNull(FileUtils.getCacheDir(context))

        val cachePath = FileUtils.getCacheDir(context).absolutePath
        assertEquals("test_path", cachePath)
    }

    @Test
    fun checkGetCacheDirWithExternalCache() {
        val context = Mockito.mock(Context::class.java)
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(file.absolutePath).thenReturn("test_path_1")
        Mockito.`when`(context.cacheDir).thenReturn(null)
        Mockito.`when`(context.externalCacheDir).thenReturn(file)

        //Check if the output is not null.
        assertNotNull(FileUtils.getCacheDir(context))

        val cachePath = FileUtils.getCacheDir(context).absolutePath
        assertEquals("test_path_1", cachePath)
    }
}
