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

package com.kevalpatel2106.ci.greenbuild.base.view

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Keval on 19-Jul-17.
 */
@RunWith(AndroidJUnit4::class)
class BaseTextViewTest {
    private var mBaseTextView: BaseTextView? = null

    @Before
    @Throws(Exception::class)
    fun init() {
        mBaseTextView = BaseTextView(InstrumentationRegistry.getTargetContext())
    }

    @Test
    @Throws(Exception::class)
    fun getTrimmedText() {
        val testVal = "123456789 "
        mBaseTextView!!.text = testVal
        assertTrue(mBaseTextView!!.getTrimmedText() == testVal.trim { it <= ' ' })
    }
}
