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

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Kevalpatel2106 on 15-May-18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
class AccountTest {
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
    fun checkUserId() {
        assertEquals(testUserId, testAccount.userId)
    }

    @Test
    @Throws(Exception::class)
    fun checkUserName() {
        assertEquals(testUserName, testAccount.username)
    }

    @Test
    @Throws(Exception::class)
    fun checkName() {
        assertEquals(testName, testAccount.name)
    }

    @Test
    @Throws(Exception::class)
    fun checkDomain() {
        assertEquals(testDomain, testAccount.serverUrl)
    }

    @Test
    @Throws(Exception::class)
    fun checkAccessToken() {
        assertEquals(testAccessToken, testAccount.accessToken)
    }

    @Test
    @Throws(Exception::class)
    fun checkAvatarUrl() {
        assertNull(testAccount.avatarUrl)

        val testAvatarUrl = "http://example.com"
        testAccount.avatarUrl = testAvatarUrl
        assertEquals(testAvatarUrl, testAccount.avatarUrl)
    }

    @Test
    @Throws(Exception::class)
    fun checkAccountId() {
        assertEquals("$testUserId-$testDomain", testAccount.accountId)
    }

    @Test
    @Throws(Exception::class)
    fun checkEquals() {
        val testAccount1 = Account(testUserId, testUserName, testName, null, testDomain, testAccessToken)
        val testAccount2 = Account("3", testUserName, testName, null, testDomain, testAccessToken)
        val testAccount3 = "string type"

        assertEquals(testAccount, testAccount1)
        assertNotEquals(testAccount, testAccount2)
        assertNotEquals(testAccount, testAccount3)
    }

    @Test
    @Throws(Exception::class)
    fun checkHashCode() {
        val testAccount1 = Account(testUserId, testUserName, testName, null, testDomain, testAccessToken)
        val testAccount2 = Account("3", testUserName, testName, null, testDomain, testAccessToken)

        assertEquals(testAccount.hashCode(), testAccount1.hashCode())
        assertNotEquals(testAccount.hashCode(), testAccount2.hashCode())
    }
}
