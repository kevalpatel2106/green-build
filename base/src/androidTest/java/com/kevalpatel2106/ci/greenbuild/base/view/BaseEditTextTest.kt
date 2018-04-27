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


import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Keval on 19-Jul-17.
 */
@RunWith(AndroidJUnit4::class)
class BaseEditTextTest {
    private var mBaseEditText: GBEditText? = null

    @Before
    @Throws(Exception::class)
    fun init() {
        mBaseEditText = GBEditText(InstrumentationRegistry.getTargetContext())
        mBaseEditText!!.setText("")
    }

    @Test
    @Throws(Exception::class)
    fun getTrimmedText() {
        val testVal = "123456789 "

        //Check for the text
        mBaseEditText!!.setText(testVal)
        assertTrue(mBaseEditText!!.getTrimmedText() == testVal.trim { it <= ' ' })

        //Check for the hint.
        mBaseEditText!!.setText("")
        mBaseEditText!!.hint = testVal
        assertTrue(mBaseEditText!!.getTrimmedText().isEmpty())
    }

    @SuppressLint("SetTextI18n")
    @Test
    @Throws(Exception::class)
    fun isEmpty() {
        //Real empty
        mBaseEditText!!.setText("")
        assertTrue(mBaseEditText!!.isEmpty())

        //Spaces
        mBaseEditText!!.setText("     ")
        assertTrue(mBaseEditText!!.isEmpty())

        //Texts
        mBaseEditText!!.setText("72354")
        assertFalse(mBaseEditText!!.isEmpty())
    }

    @SuppressLint("SetTextI18n")
    @Test
    @Throws(Exception::class)
    fun clear() {
        mBaseEditText!!.setText("123456789")
        mBaseEditText!!.clear()
        assertEquals(mBaseEditText!!.text!!.length.toLong(), 0)
    }

    @SuppressLint("SetTextI18n")
    @Test
    @Throws(Exception::class)
    fun setError() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val mockErrorText = "This is mock error."
            mBaseEditText!!.error = mockErrorText
            //            assertTrue(mBaseEditText.isFocused());
            assertEquals(mBaseEditText!!.error, mockErrorText)
        }
    }
}
