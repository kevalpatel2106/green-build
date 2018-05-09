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

package com.kevalpatel2106.greenbuild.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.util.TypedValue


/**
 * Created by Keval on 04/01/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getColorStateListCompat(@ColorRes color: Int) = ContextCompat.getColorStateList(this, color)

@SuppressLint("MissingPermission")
fun Context.vibrate(mills: Long): Boolean {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (vibrator.hasVibrator()) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(mills, VibrationEffect.DEFAULT_AMPLITUDE))
            true
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(mills)
            true
        }
    }
    return false
}

fun Context.openLink(url: String, useCustomTabs: Boolean = true) {
    if (useCustomTabs) {
        val builder = CustomTabsIntent.Builder().apply {
            setExitAnimations(this@openLink, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            setToolbarColor(getAccentColor())
            setShowTitle(true)
        }

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    } else {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }
}

@ColorInt
private fun Context.getAccentColor(): Int {
    val typedValue = TypedValue()
    val a = obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

@ColorInt
private fun Context.getPrimaryColor(): Int {
    val typedValue = TypedValue()
    val a = obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}
