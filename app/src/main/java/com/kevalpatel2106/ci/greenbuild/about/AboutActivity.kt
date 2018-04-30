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
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
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
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.utils.alert
import com.kevalpatel2106.ci.greenbuild.base.utils.openLink
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import javax.inject.Inject

class AboutActivity : MaterialAboutActivity() {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: AboutViewModel

    private var versionItem: MaterialAboutActionItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this@AboutActivity).getApplicationComponent())
                .build()
                .inject(this@AboutActivity)

        model = ViewModelProviders
                .of(this@AboutActivity, viewModelProvider)
                .get(AboutViewModel::class.java)
        super.onCreate(savedInstanceState)

        model.isCheckingUpdate.observe(this@AboutActivity, Observer {
            if (it!!) {
                versionItem?.subTextRes = (R.string.about_check_for_update)
            } else {
                versionItem?.subText = BuildConfig.VERSION_NAME
            }
            refreshMaterialAboutList()
        })
        model.isUpdateAvailable.observe(this@AboutActivity, Observer {
            it?.let { refreshMaterialAboutList() }
        })
        model.latestVersion.observe(this@AboutActivity, Observer {
            it?.let { refreshMaterialAboutList() }
        })
    }

    override fun getActivityTitle(): CharSequence = getString(R.string.application_name)


    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        val aboutList = MaterialAboutList()
        aboutList.addCard(getAboutCard())
        model.latestVersion.value?.let {
            if (model.isUpdateAvailable.value!!) aboutList.addCard(getUpdateCard(it.latestVersion))
        }
        aboutList.addCard(getSupportUsCard())
        aboutList.addCard(getContactUsCard())
        aboutList.addCard(getAuthorCard())
        return aboutList
    }


    private fun getUpdateCard(newVersion: String): MaterialAboutCard {
        return MaterialAboutCard.Builder()
                .addItem(MaterialAboutActionItem.Builder()
                        .icon(R.drawable.ic_update)
                        .setIconGravity(Gravity.START)
                        .text(R.string.check_update_new_update_available)
                        .subText(getString(R.string.about_update_card_text, newVersion))
                        .setOnClickAction({
                            //Open the play store.
                            AboutHelper.openPlayStorePage(application)
                        })
                        .build())
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
                    model.checkForUpdates()
                })
                .build()

        //open source libs
        val openSourceLibsItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_logo_opensource)
                .setIconGravity(Gravity.START)
                .text(R.string.about_open_source_libs_title)
                .setOnClickAction({
                    AboutHelper.displayOpenSourceLibs(this@AboutActivity)
                })
                .build()

        return MaterialAboutCard.Builder()
                .title(R.string.about_card_title_about)
                .addItem(versionItem)
                .addItem(openSourceLibsItem)
                .build()
    }

    private fun getSupportUsCard(): MaterialAboutCard {
        //Rate
        val rateUsItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_star_orange)
                .setIconGravity(Gravity.START)
                .text(R.string.about_rate_this_app)
                .setOnClickAction({
                    AboutHelper.openPlayStorePage(application)
                })
                .build()

        //Issue
        val reportIssueItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_bug_report_brown)
                .setIconGravity(Gravity.START)
                .text(R.string.about_report_issue_title)
                .subText(R.string.about_report_issue_subtitle)
                .setOnClickAction({
                    alert(
                            titleResource = R.string.dialog_title_report_issue,
                            messageResource = R.string.dialog_message_report_issue,
                            func = {
                                positiveButton(R.string.dialog_positive_report_issue, {
                                    openLink(getString(R.string.github_new_issue_link))
                                })
                                negativeButton(R.string.dialog_negative_report_issue, {
                                    AboutHelper.sendEmail(
                                            activity = this@AboutActivity,
                                            title = getString(R.string.issue_email_title),
                                            to = getString(R.string.support_email)
                                    )
                                })
                                neutralButton(android.R.string.cancel)
                                cancelable = true
                            }
                    )
                })
                .build()

        //Donate
        val donateItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_heart_fill_red)
                .setIconGravity(Gravity.START)
                .text(R.string.about_support_development_title)
                .setOnClickAction({
                    openLink(getString(R.string.paypal_link), false)
                })
                .build()

        //Share
        val shareItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_share_blue)
                .setIconGravity(Gravity.START)
                .text(R.string.about_share_with_friends_title)
                .setOnClickAction({
                    AboutHelper.shareWithFriends(this@AboutActivity)
                })
                .build()

        //Fork
        val forkItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_fork)
                .setIconGravity(Gravity.START)
                .text(R.string.about_fork_title)
                .setOnClickAction({
                    openLink(getString(R.string.github_fork_link))
                })
                .build()

        return MaterialAboutCard.Builder()
                .title(R.string.about_support_us_card_title)
                .addItem(rateUsItem)
                .addItem(reportIssueItem)
                .addItem(donateItem)
                .addItem(forkItem)
                .addItem(shareItem)
                .build()
    }

    private fun getAuthorCard(): MaterialAboutCard {
        //Github
        val githubItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_github_white)
                .setIconGravity(Gravity.START)
                .text(R.string.about_author_github_title)
                .subText(R.string.github_username)
                .setOnClickAction({
                    openLink(getString(R.string.author_github_link), false)
                })
                .build()


        //Twitter
        val twitterItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_twitter)
                .setIconGravity(Gravity.START)
                .text(R.string.about_author_twitter_title)
                .subText(R.string.author_twitter_username)
                .setOnClickAction({
                    openLink(getString(R.string.author_twitter_link), false)
                })
                .build()


        return MaterialAboutCard.Builder()
                .title(R.string.about_card_title_author)
                .addItem(githubItem)
                .addItem(twitterItem)
                .build()
    }

    private fun getContactUsCard(): MaterialAboutCard {

        //Email
        val emailItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_email_white)
                .setIconGravity(Gravity.START)
                .text(R.string.about_app_send_email_title)
                .subText(R.string.support_email)
                .setOnClickAction({
                    AboutHelper.sendEmail(
                            activity = this@AboutActivity,
                            to = getString(R.string.support_email),
                            title = getString(R.string.contact_us_on_support_email_title)
                    )
                })
                .build()


        //GitHub
        val githubItem = MaterialAboutActionItem.Builder()
                .icon(R.drawable.ic_github_white)
                .setIconGravity(Gravity.START)
                .text(R.string.about_app_github_title)
                .subText(getString(R.string.github_username)
                        .plus("/")
                        .plus(getString(R.string.github_project_name)))
                .setOnClickAction({
                    openLink(getString(R.string.github_project_link))
                })
                .build()

        return MaterialAboutCard.Builder()
                .title(R.string.about_app_connect_with_us_title)
                .addItem(emailItem)
                .addItem(githubItem)
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
