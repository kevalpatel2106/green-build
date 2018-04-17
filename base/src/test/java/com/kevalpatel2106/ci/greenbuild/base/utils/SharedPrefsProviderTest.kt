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

package com.kevalpatel2106.ci.greenbuild.base.utils

import android.content.Context
import com.kevalpatel2106.testutils.MockSharedPreference
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

/**
 * Created by Keval on 19-Jul-17.
 * Test class for [SharedPrefsProvider].
 */
@RunWith(JUnit4::class)
class SharedPrefsProviderTest {

    private lateinit var sharedPreferences: MockSharedPreference
    private lateinit var sharedPrefsProvider: SharedPrefsProvider
    private val TEST_KEY = "test_key"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = Mockito.mock(Context::class.java)
        sharedPreferences = MockSharedPreference()
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)

        sharedPrefsProvider = SharedPrefsProvider(sharedPreferences)
    }

    @Test
    @Throws(Exception::class)
    fun removePreferences() {
        val editor = sharedPreferences.edit()
        editor.putString(TEST_KEY, "String")
        editor.apply()

        sharedPrefsProvider.removePreferences(TEST_KEY)
        assertTrue(sharedPreferences.getString(TEST_KEY, null) == null)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences() {
        assertFalse(sharedPreferences.getInt(TEST_KEY, -1) != -1)
        sharedPrefsProvider.savePreferences(TEST_KEY, "String")
        assertTrue(sharedPreferences.getString(TEST_KEY, null) != null)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences1() {
        assertFalse(sharedPreferences.getInt(TEST_KEY, -1) != -1)
        sharedPrefsProvider.savePreferences(TEST_KEY, 1)
        assertTrue(sharedPreferences.getInt(TEST_KEY, -1) != -1)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences2() {
        assertFalse(sharedPreferences.getLong(TEST_KEY, -1) != -1L)
        sharedPrefsProvider.savePreferences(TEST_KEY, 100000L)
        assertTrue(sharedPreferences.getLong(TEST_KEY, -1) != -1L)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences3() {
        assertFalse(sharedPreferences.getBoolean(TEST_KEY, false))
        sharedPrefsProvider.savePreferences(TEST_KEY, true)
        assertTrue(sharedPreferences.getBoolean(TEST_KEY, false))
    }

    @Test
    @Throws(Exception::class)
    fun getStringFromPreferences() {
        val testVal = "String"

        val editor = sharedPreferences.edit()
        editor.putString(TEST_KEY, testVal)
        editor.apply()

        assertTrue(sharedPrefsProvider.getStringFromPreferences(TEST_KEY) == testVal)
    }

    @Test
    @Throws(Exception::class)
    fun getBoolFromPreferences() {
        val testVal = true

        val editor = sharedPreferences.edit()
        editor.putBoolean(TEST_KEY, true)
        editor.apply()


        assertTrue(sharedPrefsProvider.getBoolFromPreferences(TEST_KEY) == testVal)
    }

    @Test
    @Throws(Exception::class)
    fun getLongFromPreference() {
        val testVal = 100000L

        val editor = sharedPreferences.edit()
        editor.putLong(TEST_KEY, testVal)
        editor.apply()

        assertTrue(sharedPrefsProvider.getLongFromPreference(TEST_KEY) == testVal)
    }

    @Test
    @Throws(Exception::class)
    fun getIntFromPreference() {
        val testVal = 100

        val editor = sharedPreferences.edit()
        editor.putInt(TEST_KEY, testVal)
        editor.apply()

        assertTrue(sharedPrefsProvider.getIntFromPreference(TEST_KEY) == testVal)
    }
}
