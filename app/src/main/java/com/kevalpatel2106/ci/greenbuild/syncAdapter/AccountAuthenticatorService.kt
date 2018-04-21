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

package com.kevalpatel2106.ci.greenbuild.syncAdapter

import android.accounts.AccountManager
import android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT
import android.accounts.OnAccountsUpdateListener
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

/**
 * Created by Keval on 12-Sep-17.
 */

internal class AccountAuthenticatorService : Service() {

    private var sAuthenticator: Authenticator? = null

    private val mOnAccountsUpdateListener = OnAccountsUpdateListener {
        //TODO Handle account change
    }

    private val authenticator: Authenticator
        get() {
            if (sAuthenticator == null) {
                sAuthenticator = Authenticator(this)
            }
            return sAuthenticator!!
        }

    override fun onBind(intent: Intent): IBinder? {
        return if (intent.action == ACTION_AUTHENTICATOR_INTENT) authenticator.iBinder else null
    }

    override fun onCreate() {
        super.onCreate()

        val accountManager = AccountManager.get(this)
        accountManager.addOnAccountsUpdatedListener(mOnAccountsUpdateListener,
                Handler(),
                true)
    }

    override fun onDestroy() {
        super.onDestroy()
        val accountManager = AccountManager.get(this)
        accountManager.removeOnAccountsUpdatedListener(mOnAccountsUpdateListener)
    }
}
