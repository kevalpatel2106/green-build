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

package com.kevalpatel2106.ci.greenbuild.main

import android.arch.lifecycle.MutableLiveData
import android.support.v4.app.Fragment
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.account.Account
import com.kevalpatel2106.ci.greenbuild.base.account.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListFragment
import javax.inject.Inject

/**
 * Created by Keval on 29/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class MainActivityViewModel @Inject constructor(
        private val application: BaseApplication,
        private val accountManager: AccountsManager
) : BaseViewModel() {

    internal val currentAccount = MutableLiveData<Account>()
    internal val currentFragment = MutableLiveData<Fragment>()
    internal val currentTitle = MutableLiveData<String>()

    private val repoListFragment = RepoListFragment.getInstance()


    init {
        currentAccount.value = accountManager.getCurrentAccount()
        currentFragment.value = repoListFragment
        currentTitle.value = "Repository"
    }

    fun modelSwitchCurrentAccount(account: Account) {
        accountManager.changeCurrentAccount(account.accountId)
        currentAccount.value = account
    }

    fun onNavigationItemSelected(itemId: Int) {
        when (itemId) {
            R.id.menu_drawer_repo_listing -> {
                currentFragment.value = repoListFragment
            }
        }
    }

}