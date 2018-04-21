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

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager.KEY_BOOLEAN_RESULT
import android.accounts.NetworkErrorException
import android.content.Context
import android.os.Bundle
import com.kevalpatel2106.ci.greenbuild.base.R

/**
 * Created by Keval on 12-Sep-17.
 */

internal class Authenticator internal constructor(private val mContext: Context) : AbstractAccountAuthenticator(mContext) {

    override fun editProperties(accountAuthenticatorResponse: AccountAuthenticatorResponse, s: String): Bundle {
        throw UnsupportedOperationException()
    }

    /**
     * The user has requested to add a new account to the system. We return an
     * intent that will launch our client selection screen.
     */
    @Throws(NetworkErrorException::class)
    override fun addAccount(response: AccountAuthenticatorResponse,
                            accountType: String, authTokenType: String,
                            requiredFeatures: Array<String>, options: Bundle): Bundle {
        //        final Intent intent = new Intent(mContext, ClientSelectionActivity.class);
        //        bundle.putParcelable(KEY_INTENT, intent);
        return Bundle()
    }

    @Throws(NetworkErrorException::class)
    override fun confirmCredentials(accountAuthenticatorResponse: AccountAuthenticatorResponse, account: Account, bundle: Bundle): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun getAuthToken(accountAuthenticatorResponse: AccountAuthenticatorResponse, account: Account, s: String, bundle: Bundle): Bundle {
        throw UnsupportedOperationException()
    }

    override fun getAuthTokenLabel(authTokenType: String): String? {
        return if (mContext.getString(R.string.sync_adapter_account_type) == authTokenType) {
            authTokenType
        } else {
            null
        }
    }

    override fun updateCredentials(
            response: AccountAuthenticatorResponse, account: Account,
            authTokenType: String, options: Bundle): Bundle {

        //        final Intent intent = new Intent(mContext, ClientSelectionActivity.class);
        //        bundle.putParcelable(KEY_INTENT, intent);
        return Bundle()
    }

    @Throws(NetworkErrorException::class)
    override fun hasFeatures(accountAuthenticatorResponse: AccountAuthenticatorResponse, account: Account, strings: Array<String>): Bundle {
        val result = Bundle()
        result.putBoolean(KEY_BOOLEAN_RESULT, false)
        return result
    }
}
