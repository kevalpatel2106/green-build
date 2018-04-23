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

package com.kevalpatel2106.ci.greenbuild.base.account

import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.kevalpatel2106.ci.greenbuild.base.R
import com.kevalpatel2106.ci.greenbuild.base.utils.SharedPrefsProvider


/**
 * Created by Keval on 16/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class AccountsManager(private val context: Context,
                      private val sharedPrefsProvider: SharedPrefsProvider) {
    companion object {
        private const val PREF_KEY_CURRENT_ACCOUNT_ID = "pref_key_current_account_id"
        private const val SYNC_FREQUENCY = 3600L  // 1 hour (in seconds)

        //Account data bundle
        private const val ARG_ID = "arg_local_id"
        private const val ARG_USER_ID = "arg_user_id"
        private const val ARG_USERNAME = "arg_username"
        private const val ARG_NAME = "arg_name"
        private const val ARG_AUTH_TOKEN = "arg_auth_token"
        private const val ARG_IMAGE_URL = "arg_image_url"
        private const val ARG_DOMAIN_NAME = "arg_domain_name"
    }

    private val type = context.getString(R.string.sync_adapter_account_type)
    private val manager = context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager

    /**
     * Check if any account is registered or not?
     *
     * @return True if at least one account is registered else false.
     */
    fun isAnyAccountRegistered(): Boolean {
        return manager.getAccountsByType(type).isNotEmpty()
    }

    fun changeCurrentAccount(accountId: String) {
        sharedPrefsProvider.savePreferences(PREF_KEY_CURRENT_ACCOUNT_ID, accountId)
    }

    fun getCurrentAccount(): Account {
        return with(sharedPrefsProvider.getStringFromPreferences(PREF_KEY_CURRENT_ACCOUNT_ID, null)) {

            return@with if (this == null) {
                // No current account selected.
                // Set first account as selected
                getAllAccounts().first().apply {

                    //Change the current account id.
                    changeCurrentAccount(this.accountId)
                }
            } else {
                // Get the current account from account id.
                getAccount(this) ?: throw IllegalStateException("No account added.")
            }
        }
    }

    /**
     * Get the list of all the user [Account] currently registered into the application. If there
     * are no accounts registered yet, it will return empty [ArrayList].
     */
    fun getAllAccounts(): ArrayList<Account> {
        val accounts = arrayListOf<Account>()
        with(manager.getAccountsByType(type)) {
            this.map { parseAccount(it) }
                    .forEach { accounts.add(it) }
        }
        return accounts
    }


    fun getAccount(accountId: String): Account? {
        manager.getAccountsByType(type)
                .filter { manager.getUserData(it, ARG_ID) == accountId }
                .map { parseAccount(it) }
                .forEach { return it }
        return null
    }

    /**
     * Save the new [Account]. If the account is already present, it will update the current account.
     * If the account is not present, this will create new account.
     *
     * @return If the account is added/updated successfully method will return true else false.
     */
    fun saveAccount(account: Account): Boolean {
        val androidAccount = android.accounts.Account(
                "${account.username}-${account.serverUrl.replace("https://api.", "")}",
                context.getString(R.string.sync_adapter_account_type)
        )

        val userData = Bundle().apply {
            putString(ARG_ID, account.accountId)
            putString(ARG_USER_ID, account.userId)
            putString(ARG_NAME, account.name)
            putString(ARG_USERNAME, account.username)
            putString(ARG_AUTH_TOKEN, account.accessToken)
            putString(ARG_IMAGE_URL, account.avatarUrl)
            putString(ARG_DOMAIN_NAME, account.serverUrl)
        }

        if (manager.addAccountExplicitly(androidAccount, account.accessToken, userData)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(
                    androidAccount,
                    context.getString(R.string.sync_adapter_account_authority),
                    1
            )

            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(
                    androidAccount,
                    context.getString(R.string.sync_adapter_account_authority),
                    true
            )

            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(
                    androidAccount,
                    context.getString(R.string.sync_adapter_account_authority),
                    Bundle(),
                    SYNC_FREQUENCY
            )
            return true
        }
        return false
    }

    /**
     * Delete the [Account] with [accountId]. If the account with [accountId] is not present, than
     * method will return false.
     */
    fun deleteAccounts(accountId: String): Boolean {
        var isAnyAccountDeleted = false
        manager.getAccountsByType(type)
                .filter { manager.getUserData(it, ARG_ID) == accountId }
                .forEach {
                    @Suppress("DEPRECATION")
                    manager.removeAccount(it, {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }, Handler(Looper.getMainLooper()))
                    isAnyAccountDeleted = true
                }

        return isAnyAccountDeleted
    }

    fun getAccessToken(accountId: String): String? {
        return getAccount(accountId)?.accessToken
    }

    private fun parseAccount(androidAccount: android.accounts.Account): Account {
        return Account(
                name = manager.getUserData(androidAccount, ARG_NAME),
                accessToken = manager.getUserData(androidAccount, ARG_AUTH_TOKEN),
                avatarUrl = manager.getUserData(androidAccount, ARG_IMAGE_URL),
                serverUrl = manager.getUserData(androidAccount, ARG_DOMAIN_NAME),
                userId = manager.getUserData(androidAccount, ARG_USER_ID),
                username = manager.getUserData(androidAccount, ARG_USERNAME)
        )
    }
}
