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

package com.kevalpatel2106.ci.greenbuild.about

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import com.cocosw.bottomsheet.BottomSheet
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.greenbuild.utils.openLink
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder


/**
 * Created by Kevalpatel2106 on 30-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal object AboutHelper {

    fun shareWithFriends(context: Context) {
        context.startActivity(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, context.getString(R.string.app_invite_message))
        })
    }

    fun openPlayStorePage(application: Application) {
        try {
            val uri = Uri.parse("market://details?id=" + application.packageName
                    .replace(".debug", ""))

            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            application.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            application.openLink(application.getString(R.string.play_store_url))
        }
    }

    fun sendEmail(activity: Activity, title: String? = null, mesaage: String? = null, to: String) {
        val bottomSheet = BottomSheet.Builder(activity).title(activity.getString(R.string.open_email_bottom_dialog_title))

        //Get the list of email clients.
        val emailAppsList = getEmailApplications(activity.packageManager)

        //Add each items to the bottom sheet
        for (i in 0 until emailAppsList.size) {
            val s = emailAppsList[i]
            getApplicationName(s.activityInfo.packageName, activity.packageManager)?.let {
                bottomSheet.sheet(i, s.loadIcon(activity.packageManager), it)
            }
        }

        //On clicking any item, open the email application
        bottomSheet.listener { _, pos ->
            val intent = Intent().apply {
                component = ComponentName(emailAppsList[pos].activityInfo.packageName, emailAppsList[pos].activityInfo.name)
                action = Intent.ACTION_SEND
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, to)
                title?.let { putExtra(Intent.EXTRA_SUBJECT, title) }
                mesaage?.let { putExtra(Intent.EXTRA_TEXT, mesaage) }
            }
            activity.startActivity(intent)
        }
        bottomSheet.build()
        bottomSheet.show()
    }

    fun displayOpenSourceLibs(context: Context) {
        LibsBuilder().withActivityStyle(Libs.ActivityStyle.DARK)
                .withActivityTitle(context.getString(R.string.title_activity_open_source_libs))
                .withAutoDetect(true)
                .withAboutIconShown(true)
                .withAboutVersionShownName(true)
                .withAboutVersionShownCode(false)
                .withLicenseShown(true)
                .start(context)
    }


    private fun getApplicationName(packageName: String, packageManager: PackageManager): String? {
        val ai: ApplicationInfo? = try {
            packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
        return if (ai != null) packageManager.getApplicationLabel(ai).toString() else null
    }

    private fun getEmailApplications(packageManager: PackageManager): List<ResolveInfo> {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        return packageManager.queryIntentActivities(emailIntent, 0)
    }

}
