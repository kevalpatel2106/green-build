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

package com.kevalpatel2106.greenbuild.travisInterface.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.view.MenuItem
import com.kevalpatel2106.greenbuild.travisInterface.R
import kotlinx.android.synthetic.main.activity_travis_help.*
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorCompat
import com.kevalpatel2106.ci.greenbuild.base.utils.openLink


class TravisHelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travis_help)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_activity_travis_help)

        if (intent == null) throw IllegalArgumentException("No profile url passed.")


        //Set the profile link.
        val profileUrl = intent.getStringExtra(ARG_PROFILE_URL) ?: return
        val hint2 = SpannableString(travis_help_tv_2.text)
        hint2.setSpan(
                object : ClickableSpan() {
                    override fun onClick(textView: View) {
                        openLink(profileUrl)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                },
                hint2.indexOf(". ") + 2,
                hint2.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        travis_help_tv_2.text = hint2
        travis_help_tv_2.movementMethod = LinkMovementMethod.getInstance()
        travis_help_tv_2.highlightColor = getColorCompat(R.color.colorAccent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARG_PROFILE_URL = "arg_profile_url"

        fun launch(context: Context, profileUrl: String?) {
            context.startActivity(Intent(context, TravisHelpActivity::class.java).apply {
                putExtra(ARG_PROFILE_URL, profileUrl)
            })
        }
    }
}
