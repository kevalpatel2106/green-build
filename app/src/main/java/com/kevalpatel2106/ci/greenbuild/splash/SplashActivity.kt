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

package com.kevalpatel2106.ci.greenbuild.splash

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kevalpatel2106.ci.greenbuild.base.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.ciSelector.CiSelectorActivity
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import com.kevalpatel2106.ci.greenbuild.main.MainActivity
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Kevalpatel2106 on 16-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class SplashActivity : AppCompatActivity() {

    @Inject
    internal lateinit var accountsManager: AccountsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@SplashActivity)

        signInAsAnonymousUser()
    }

    /**
     * Sign In the user as anonymous user using firebase auth.
     */
    private fun signInAsAnonymousUser() {
        with(FirebaseAuth.getInstance()) {
            if (this.currentUser == null) {
                this.signInAnonymously().addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInAsAnonymousUser:success ${it.result}")
                        initFlow()
                    } else {
                        // If sign in fails.
                        // Don't worry we will try again.
                        initFlow()
                    }
                }
            } else {
                // User already logged in.
                // Do nothing
                initFlow()
            }
        }
    }

    private fun initFlow() {
        if (!accountsManager.isAnyAccountRegistered()) {
            CiSelectorActivity.launch(this@SplashActivity)
        } else {
            MainActivity.launch(this@SplashActivity)
        }
        finish()
    }

    companion object {

        fun launch(application: Application) {
            application.startActivity(Intent(application, SplashActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}
