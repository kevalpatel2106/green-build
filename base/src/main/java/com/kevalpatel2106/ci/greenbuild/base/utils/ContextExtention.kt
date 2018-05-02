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

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.kevalpatel2106.ci.greenbuild.base.R


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

@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun Context.isScreenOn(): Boolean {
    val powerManager = getSystemService(POWER_SERVICE) as PowerManager
    return powerManager.isScreenOn
}

@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun Context.isDeviceLocked(): Boolean {
    val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    return keyguardManager.inKeyguardRestrictedInputMode()
}


/**
 * Show the toast for [Toast.LENGTH_SHORT] duration.
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Show the toast for [Toast.LENGTH_SHORT] duration.
 */
fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.openLink(url: String, useCustomTabs: Boolean = true) {
    if (useCustomTabs) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(getColorCompat(R.color.colorAccent))
        builder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    } else {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }
}
