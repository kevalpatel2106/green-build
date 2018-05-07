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
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.greenbuild.utils.arch.BaseViewModel
import com.kevalpatel2106.greenbuild.utils.arch.SingleLiveEvent
import com.kevalpatel2106.greenbuild.utils.arch.recall
import com.kevalpatel2106.ci.greenbuild.buildList.RecentBuildsFragment
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by Keval on 29/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class MainActivityViewModel @Inject constructor(
        private val application: BaseApplication,
        private val accountManager: AccountsManager,
        private val compatibilityCheck: CompatibilityCheck
) : BaseViewModel() {

    internal val reinjectDependency = SingleLiveEvent<Unit>()

    internal val allAccounts = MutableLiveData<ArrayList<Account>>()
    internal val currentAccount = MutableLiveData<Account>()
    internal val currentFragment = MutableLiveData<Fragment>()
    internal val currentTitle = MutableLiveData<String>()
    internal val isAccountsOpen = MutableLiveData<Boolean>()

    internal val errorLoggingOut = SingleLiveEvent<String>()
    internal val isLoggingOut = MutableLiveData<Boolean>()

    private val repoListFragment = RepoListFragment.getInstance()
    private val recentBuildsFragment = RecentBuildsFragment.getInstance()

    init {
        allAccounts.value = ArrayList()
        isAccountsOpen.value = false
        refresh()
        isLoggingOut.value = false
    }

    fun refresh() {
        currentAccount.value = accountManager.getCurrentAccount()

        //Load the first fragment.
        currentFragment.value = when {
            compatibilityCheck.isRepoListingSupported() -> {
                currentTitle.value = application.getString(R.string.title_activity_repo)
                repoListFragment
            }
            compatibilityCheck.isRecentBuildsListSupported() -> {
                currentTitle.value = application.getString(R.string.title_activity_builds_list)
                repoListFragment
            }
            else -> throw IllegalStateException("This CI doesn't support any of the drawer feature.")
        }
    }

    fun modelSwitchCurrentAccount(account: Account) {
        accountManager.changeCurrentAccount(account.accountId)
        currentAccount.value = account
        reinjectDependency.recall()
    }

    fun switchToRepositoryList() {
        currentFragment.value = repoListFragment
        currentTitle.value = application.getString(R.string.title_activity_repo)
    }

    fun switchToBuildsList() {
        currentFragment.value = recentBuildsFragment
        currentTitle.value = application.getString(R.string.title_activity_builds_list)
    }

    fun logoutCurrentAccount() {
        currentAccount.value?.let {
            accountManager.deleteAccount(it.accountId)
                    .doOnSubscribe {
                        isLoggingOut.value = true
                    }
                    .doOnError {
                        isLoggingOut.value = false
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        isLoggingOut.value = false

                        currentAccount.value = accountManager.getCurrentAccount()
                        reinjectDependency.recall()
                    }, {
                        errorLoggingOut.value = application.getString(R.string.error_logging_out)
                    })
        }
    }
}
