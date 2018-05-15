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

package com.kevalpatel2106.grrenbuild.entities

import android.os.Parcel
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Kevalpatel2106 on 15-May-18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(AndroidJUnit4::class)
class AccountUiTest {
    private val testUserId = "435"
    private val testUserName = "jondoe"
    private val testName = "jon doe"
    private val testDomain = "http://example.com"
    private val testAccessToken = "7843dhfajsd723"

    private lateinit var testAccount: Account

    @Before
    fun setUp() {
        testAccount = Account(testUserId, testUserName, testName, null, testDomain, testAccessToken)
    }

    @Test
    @Throws(Exception::class)
    fun checkParcelable() {
        val parcel = Parcel.obtain()
        testAccount.writeToParcel(parcel, testAccount.describeContents())
        parcel.setDataPosition(0)

        val createdFromParcel = Account.createFromParcel(parcel)

        //Check
        assertEquals(testUserId, createdFromParcel.userId)
        assertEquals(testUserName, createdFromParcel.username)
        assertEquals(testName, createdFromParcel.name)
        assertNull(createdFromParcel.avatarUrl)
        assertEquals(testDomain, createdFromParcel.serverUrl)
        assertEquals(testAccessToken, createdFromParcel.accessToken)
        assertEquals("$testUserId-$testDomain", createdFromParcel.accountId)
    }
}
