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

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.kevalpatel2106.ci.greenbuild.BuildConfig
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.utils.openLink

class AboutActivity : MaterialAboutActivity() {


    private lateinit var model :AboutViewModel
    private var versionItem: MaterialAboutActionItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.isCheckingUpdate.observe(this@AboutActivity, Observer {
            if (it!!) {
                versionItem?.subTextRes = (R.string.about_check_for_update)
            } else {
                versionItem?.subText = BuildConfig.VERSION_NAME
            }
            refreshMaterialAboutList()
        })
    }

    override fun getActivityTitle(): CharSequence = getString(R.string.application_name)


    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        val aboutList = MaterialAboutList()
        aboutList.addCard(getAboutCard())
        aboutList.addCard(getUpdateCard("10.2"))

        aboutList.addCard(getSupportUsCard())
        aboutList.addCard(getJoinUsCard())
        aboutList.addCard(getAuthorCard())
        return aboutList
    }


    private fun getUpdateCard(newVersion: String): MaterialAboutCard {
        return MaterialAboutCard.Builder()
                .addItem(MaterialAboutActionItem.Builder()
                        .icon(R.drawable.ic_update)
                        .setIconGravity(Gravity.START)
                        .text(R.string.check_update_new_update_available)
                        .subText("New version is $newVersion. Click here to newVersion.")
                        .setOnClickAction({
                            //Open the play store.
                            openLink(getString(R.string.rate_app_url))
                        })
                        .build())
                .build()
    }

    private fun getSupportUsCard(): MaterialAboutCard {
        //Rate
        val rateUsItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_star_orange)
                .setIconGravity(Gravity.START)
                .text(R.string.about_rate_this_app)
                .setOnClickAction({
                    model.handleRateUs()
                })
                .build()

        //Issue
        val reportIssueItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_bug_report_brown)
                .setIconGravity(Gravity.START)
                .text(R.string.about_report_issue_title)
                .subText(R.string.about_report_issue_subtitle)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()

        //Donate
        val donateItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_heart_fill_red)
                .setIconGravity(Gravity.START)
                .text(R.string.about_support_development_title)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()

        //Share
        val shareItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_share_blue)
                .setIconGravity(Gravity.START)
                .text(R.string.about_share_with_friends_title)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()

        return MaterialAboutCard.Builder()
                .title(R.string.about_support_us_card_title)
                .addItem(rateUsItem)
                .addItem(reportIssueItem)
                .addItem(donateItem)
                .addItem(shareItem)
                .build()
    }


    private fun getAboutCard(): MaterialAboutCard {
        //CheckVersionResponse
        versionItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_version_white)
                .setIconGravity(Gravity.START)
                .text(R.string.about_check_version_title)
                .subText(BuildConfig.VERSION_NAME)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()

        //open source libs
        val openSourceLibsItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_logo_opensource)
                .setIconGravity(Gravity.START)
                .text(R.string.about_open_source_libs_title)
                .setOnClickAction({
                    model.handleOpenSourceLibs(this@AboutActivity)
                })
                .build()

        return MaterialAboutCard.Builder()
                .title(R.string.about_card_title_about)
                .addItem(versionItem)
                .addItem(openSourceLibsItem)
                .build()
    }


    private fun getAuthorCard(): MaterialAboutCard {
        //Github
        val githubItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_github_white)
                .setIconGravity(Gravity.START)
                .text(R.string.about_author_github_title)
                .subText(R.string.about_author_github_subtitle)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()


        //Twitter
        val twitterItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_twitter)
                .setIconGravity(Gravity.START)
                .text(R.string.about_author_twitter_title)
                .subText(R.string.about_author_twitter_subtitle)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()


        return MaterialAboutCard.Builder()
                .title(R.string.about_card_title_author)
                .addItem(githubItem)
                .addItem(twitterItem)
                .build()
    }

    private fun getJoinUsCard(): MaterialAboutCard {

        //Email
        val emailItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_email_white)
                .setIconGravity(Gravity.START)
                .text(R.string.about_app_send_email_title)
                .subText(R.string.support_email)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()


        //Twitter
        val twitterItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_github_white)
                .setIconGravity(Gravity.START)
                .text(R.string.about_app_github_title)
                .subText(R.string.about_app_github_subtitle)
                .setOnClickAction({
                    // TODO ("Yet to implement")
                })
                .build()

        return MaterialAboutCard.Builder()
                .title(R.string.about_app_connect_with_us_title)
                .addItem(emailItem)
                .addItem(twitterItem)
                .build()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return false
    }

    companion object {

        /**
         * Launch the [AboutActivity] activity.
         *
         * @param context Instance of the caller.
         */
        @JvmStatic
        internal fun launch(context: Context) {
            val launchIntent = Intent(context, AboutActivity::class.java)
            context.startActivity(launchIntent)
        }
    }
}
