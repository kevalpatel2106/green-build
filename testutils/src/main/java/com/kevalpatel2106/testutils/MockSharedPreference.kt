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

import android.content.SharedPreferences
import java.util.*


/**
 * Created by Kevalpatel2106 on 02-Jan-18.
 * Mock implementation of shared preference, which just saves data in memory using map.
 *
 * @see <a href="https://gist.github.com/amardeshbd/354173d00b988574ee5019c4ba0c8a0b">Reference</a>
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class MockSharedPreference : SharedPreferences {

    private val preferenceMap: HashMap<String, Any?> = HashMap()
    private val preferenceEditor: MockSharedPreferenceEditor

    init {
        preferenceEditor = MockSharedPreferenceEditor(preferenceMap)
    }

    override fun getAll(): Map<String, *> {
        return preferenceMap
    }

    override fun getString(s: String, s1: String?): String? {
        return preferenceMap[s] as String? ?: s1
    }

    override fun getStringSet(s: String, set: Set<String>?): Set<String>? {
        @Suppress("UNCHECKED_CAST")
        return preferenceMap[s] as Set<String>
    }

    override fun getInt(s: String, i: Int): Int {
        return preferenceMap[s] as Int? ?: i
    }

    override fun getLong(s: String, l: Long): Long {
        return preferenceMap[s] as Long? ?: l
    }

    override fun getFloat(s: String, v: Float): Float {
        return preferenceMap[s] as Float? ?: v
    }

    override fun getBoolean(s: String, b: Boolean): Boolean {
        return preferenceMap[s] as Boolean? ?: b
    }

    override fun contains(s: String): Boolean {
        return preferenceMap.containsKey(s)
    }

    override fun edit(): SharedPreferences.Editor {
        return preferenceEditor
    }

    override fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {

    }

    override fun unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {

    }

    class MockSharedPreferenceEditor(private val preferenceMap: HashMap<String, Any?>) : SharedPreferences.Editor {

        override fun putString(s: String, s1: String?): SharedPreferences.Editor {
            preferenceMap.put(s, s1)
            return this
        }

        override fun putStringSet(s: String, set: Set<String>?): SharedPreferences.Editor {
            preferenceMap.put(s, set)
            return this
        }

        override fun putInt(s: String, i: Int): SharedPreferences.Editor {
            preferenceMap.put(s, i)
            return this
        }

        override fun putLong(s: String, l: Long): SharedPreferences.Editor {
            preferenceMap.put(s, l)
            return this
        }

        override fun putFloat(s: String, v: Float): SharedPreferences.Editor {
            preferenceMap.put(s, v)
            return this
        }

        override fun putBoolean(s: String, b: Boolean): SharedPreferences.Editor {
            preferenceMap.put(s, b)
            return this
        }

        override fun remove(s: String): SharedPreferences.Editor {
            preferenceMap.remove(s)
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            preferenceMap.clear()
            return this
        }

        override fun commit(): Boolean {
            return true
        }

        override fun apply() {
            // Nothing to do, everything is saved in memory.
        }
    }

}