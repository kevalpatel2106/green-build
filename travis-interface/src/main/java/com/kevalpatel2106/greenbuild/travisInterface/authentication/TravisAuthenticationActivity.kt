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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import androidx.core.os.postDelayed
import com.kevalpatel2106.ci.greenbuild.base.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.AnalyticsEvents
import com.kevalpatel2106.ci.greenbuild.base.logEvent
import com.kevalpatel2106.greenbuild.utils.showSnack
import com.kevalpatel2106.greenbuild.travisInterface.R
import com.kevalpatel2106.greenbuild.travisInterface.TravisModuleCallbacks
import com.kevalpatel2106.greenbuild.travisInterface.di.DaggerTravisDiComponent
import kotlinx.android.synthetic.main.activity_travis_authentication.*
import javax.inject.Inject


/**
 * This [AppCompatActivity] will take the API access token and validate the token by calling for the
 * user profile. If the token is valid, application will redirect the user to display the list of
 * travis repository.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class TravisAuthenticationActivity : AppCompatActivity(), TextWatcher {

    @Inject
    internal lateinit var accountManager: AccountsManager

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: TravisAuthenticationViewModel

    private var clipboardManager: ClipboardManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerTravisDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@TravisAuthenticationActivity)

        model = ViewModelProviders.of(this, viewModelFactory)
                .get(TravisAuthenticationViewModel::class.java)

        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        setContentView(R.layout.activity_travis_authentication)

        setSupportActionBar(auth_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Set the title.
        authentication_ci_provider_iv.setImageResource(intent.getIntExtra(ARG_CI_ICON, 0))
        authentication_ci_provider_tv.text = intent.getStringExtra(ARG_CI_NAME)

        //Set the api base url.
        val argumentBaseUrl: String? = with(intent.getStringExtra(ARG_API_BASE_URL)) {
            if (this.isNullOrEmpty()) {
                authentication_base_url_til.visibility = View.VISIBLE
                authentication_base_url_et.setSelection(authentication_base_url_et.text!!.length)
                authentication_base_url_et.addTextChangedListener(this@TravisAuthenticationActivity)

                return@with null
            } else {
                authentication_base_url_til.visibility = View.GONE
                return@with this
            }
        }

        authentication_btn.setOnClickListener {
            //Reset all the errors
            authentication_base_url_til.error = ""
            authentication_token_til.error = ""

            model.validateAuthToken(
                    accessToken = authentication_token_et.text?.trim().toString(),
                    apiUrl = argumentBaseUrl
                            ?: model.prepareApiUrl(authentication_base_url_et.text.toString())
            )
        }

        model.authenticatedAccount.observe(this@TravisAuthenticationActivity, Observer {
            it?.let {
                showSnack(getString(R.string.account_successfully_authenticated))
                logEvent(AnalyticsEvents.EVENT_ADD_CI, Bundle().apply {
                    putString(AnalyticsEvents.ACCOUNT_TYPE, argumentBaseUrl)
                })

                Handler().postDelayed(3000) {
                    TravisModuleCallbacks.instance.openHome(this@TravisAuthenticationActivity)
                    finish()
                }
            }
        })

        model.authenticationError.observe(this@TravisAuthenticationActivity, Observer {
            it?.let { showSnack(it) }
        })

        model.accessTokenValidationError.observe(this@TravisAuthenticationActivity, Observer {
            it?.let {
                authentication_token_til.error = it
                authentication_token_et.requestFocus()
            }
        })

        model.serverDomainValidationError.observe(this@TravisAuthenticationActivity, Observer {
            it?.let {
                authentication_base_url_til.error = it
                authentication_base_url_et.requestFocus()
            }
        })

        model.isValidationInProgress.observe(this@TravisAuthenticationActivity, Observer {
            it?.let { authentication_btn.displayLoader(it) }
        })

        travis_access_token_hint_tv.setOnClickListener {
            TravisHelpActivity.launch(
                    context = this@TravisAuthenticationActivity,
                    profileUrl = model.getProfileUrl(argumentBaseUrl
                            ?: authentication_base_url_et.text.toString())
            )
        }

        authentication_paste_btn.setOnClickListener {
            clipboardManager?.let {
                authentication_token_et.setText(it.primaryClip.getItemAt(0).text)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        authentication_paste_btn.isEnabled = clipboardManager != null
                && clipboardManager!!.hasPrimaryClip()  /* Check that there is there anything in clipboard? */
                && clipboardManager!!.primaryClipDescription.hasMimeType(MIMETYPE_TEXT_PLAIN)   /* Check that the item in clipboard is text. */
    }

    override fun afterTextChanged(p0: Editable?) {
        //Do nothing
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //Do nothing
    }

    override fun onTextChanged(chars: CharSequence, p1: Int, p2: Int, p3: Int) {
        if (chars.contains(".")) {
            if (Patterns.WEB_URL.matcher(chars.toString()).matches()) {
                authentication_base_url_til.error = ""
            } else {
                authentication_base_url_til.error = "Invalid URL."
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        supportFinishAfterTransition()
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val ARG_API_BASE_URL = "arg_api_base_url"
        private const val ARG_CI_NAME = "arg_ci_name"
        private const val ARG_CI_ICON = "arg_ci_icon"

        internal fun launch(context: Context,
                            baseUrl: String?,
                            ciName: String,
                            @DrawableRes ciIcon: Int,
                            options: ActivityOptionsCompat? = null) {
            context.startActivity(Intent(context, TravisAuthenticationActivity::class.java).apply {
                putExtra(ARG_API_BASE_URL, baseUrl)
                putExtra(ARG_CI_NAME, ciName)
                putExtra(ARG_CI_ICON, ciIcon)
            }, options?.toBundle())
        }
    }
}
