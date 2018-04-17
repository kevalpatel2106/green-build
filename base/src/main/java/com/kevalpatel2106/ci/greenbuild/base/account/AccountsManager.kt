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

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kevalpatel2106.ci.greenbuild.base.utils.SharedPrefsProvider

/**
 * Created by Keval on 16/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
class AccountsManager(private val sharedPrefsProvider: SharedPrefsProvider) {
    private val PREFF_KEY_ACCOUNTS = "accounts_key"

    /**
     * Get the list of all the user [Account] currently registered into the application. If there
     * are no accounts registered yet, it will return empty [ArrayList].
     */
    fun getAllAccounts(): ArrayList<Account> {
        with(sharedPrefsProvider.getStringFromPreferences(PREFF_KEY_ACCOUNTS)) {
            if (!this.isNullOrEmpty()) {
                val token = object : TypeToken<ArrayList<Account>>() {}
                return Gson().fromJson<ArrayList<Account>>(this, token.type)
            }
        }
        return ArrayList(0)
    }

    /**
     * Save the new [Account]. If the account is already present, it will update the current account.
     * If the account is not present, this will create new account.
     *
     * @return If the account is added/updated successfully method will return true else false.
     */
    fun saveAccounts(account: Account): Boolean {
        with(getAllAccounts()) {
            when {
                this.isEmpty() -> this.add(account)
                this.contains(account) -> this[this.indexOf(account)] = account
                else -> this.add(account)
            }
            sharedPrefsProvider.savePreferences(PREFF_KEY_ACCOUNTS, Gson().toJson(this))
        }
        return true
    }

    /**
     * Delete the [Account] with [accountId]. If the account with [accountId] is not present, than
     * method will return false.
     */
    fun deleteAccounts(accountId: String): Boolean {
        //TODO
        return true
    }

    fun getAccessToken(accountId: String): String? {
        getAllAccounts().filter { it.accountId == accountId }.forEach { return it.accessToken }
        return null
    }

    fun getAccessToken(userId: String, serverUrl: String): String? {
        return getAccessToken(Account.preppareAccountId(userId, serverUrl))
    }
}