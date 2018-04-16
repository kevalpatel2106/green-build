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

import android.app.KeyguardManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.PowerManager
import android.os.Vibrator
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by Keval on 03/03/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(Enclosed::class)
class ContextExtensionKtTest {


    @RunWith(RobolectricTestRunner::class)
    @Config(sdk = [21], manifest = Config.NONE)
    class ContextExtensionKtApi19Test {

        @Test
        @Throws(Exception::class)
        fun testGetColor() {
            Assert.assertEquals(Color.WHITE, RuntimeEnvironment.application.getColorCompat(android.R.color.white))
        }

        @Test
        @Throws(Exception::class)
        fun testVibrate_NoVibrator() {
            val vibrator = Mockito.mock(Vibrator::class.java)
            Mockito.`when`(vibrator.hasVibrator()).thenReturn(false)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(vibrator)

            Assert.assertFalse(context.vibrate(10000))
        }

        @Test
        @Throws(Exception::class)
        fun testVibrate_HasVibrator() {
            val vibrator = Mockito.mock(Vibrator::class.java)
            Mockito.`when`(vibrator.hasVibrator()).thenReturn(true)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(vibrator)

            Assert.assertTrue(context.vibrate(10000))
        }

        @Test
        @Throws(Exception::class)
        fun testDeviceLocked_Positive() {
            val keyguardManager = Mockito.mock(KeyguardManager::class.java)
            Mockito.`when`(keyguardManager.inKeyguardRestrictedInputMode()).thenReturn(true)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(keyguardManager)

            Assert.assertTrue(context.isDeviceLocked())
        }

        @Test
        @Throws(Exception::class)
        fun testDeviceLocked_Negative() {
            val keyguardManager = Mockito.mock(KeyguardManager::class.java)
            Mockito.`when`(keyguardManager.inKeyguardRestrictedInputMode()).thenReturn(false)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(keyguardManager)

            Assert.assertFalse(context.isDeviceLocked())
        }
    }

    @RunWith(RobolectricTestRunner::class)
    @Config(sdk = [26], manifest = Config.NONE)
    class ContextExtensionKtApi26Test {

        @Test
        @Throws(Exception::class)
        fun testGetColor() {
            Assert.assertEquals(Color.WHITE, RuntimeEnvironment.application.getColorCompat(android.R.color.white))
        }

        @Test
        @Throws(Exception::class)
        fun testVibrate_NoVibrator() {
            val vibrator = Mockito.mock(Vibrator::class.java)
            Mockito.`when`(vibrator.hasVibrator()).thenReturn(false)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(vibrator)

            Assert.assertFalse(context.vibrate(10000))
        }

        @Test
        @Throws(Exception::class)
        fun testVibrate_HasVibrator() {
            val vibrator = Mockito.mock(Vibrator::class.java)
            Mockito.`when`(vibrator.hasVibrator()).thenReturn(true)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(vibrator)

            Assert.assertTrue(context.vibrate(10000))
        }

        @Test
        @Throws(Exception::class)
        fun testIsScreenOn_Positive() {
            val powerManager = Mockito.mock(PowerManager::class.java)
            Mockito.`when`(powerManager.isScreenOn).thenReturn(true)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(powerManager)

            Assert.assertTrue(context.isScreenOn())
        }

        @Test
        @Throws(Exception::class)
        fun testIsScreenOn_Negative() {
            val powerManager = Mockito.mock(PowerManager::class.java)
            Mockito.`when`(powerManager.isScreenOn).thenReturn(false)

            val context = Mockito.spy(RuntimeEnvironment.application)
            Mockito.`when`(context.getSystemService(anyString())).thenReturn(powerManager)

            Assert.assertFalse(context.isScreenOn())
        }
    }
}
