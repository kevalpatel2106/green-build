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
import com.kevalpatel2106.ci.greenbuild.base.AccountsManager
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.buildList.RecentBuildsFragment
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListFragment
import com.kevalpatel2106.greenbuild.utils.arch.BaseViewModel
import com.kevalpatel2106.greenbuild.utils.arch.SingleLiveEvent
import com.kevalpatel2106.grrenbuild.entities.Account
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

    /**
     * [MutableLiveData] of the list of all the [Account]s registered in the application.
     */
    internal val allAccounts = MutableLiveData<ArrayList<Account>>()

    /**
     * [MutableLiveData] of the currently selected CI [Account].
     */
    internal val currentAccount = MutableLiveData<Account>()

    /**
     * [MutableLiveData] of the currently selected [Fragment].
     */
    internal val currentFragment = MutableLiveData<Fragment>()

    /**
     * [MutableLiveData] of the string title for the [MainActivity]. The title of the screen changes
     * based on [currentFragment].
     */
    internal val currentTitle = MutableLiveData<String>()

    /**
     * [SingleLiveEvent] of the error message that explains error occurred while logging out. If there
     * are no errors to display, value will be null.
     */
    internal val errorLoggingOut = SingleLiveEvent<String>()

    /**
     * [MutableLiveData] of the [Boolean]  that indicates if the logging out is processing or not.
     * True if the application is currently logging out from the CI [Account],
     */
    internal val isLoggingOut = MutableLiveData<Boolean>()

    /**
     * [RepoListFragment] to display.
     */
    private lateinit var repoListFragment: RepoListFragment

    /**
     * [RecentBuildsFragment] to display.
     */
    private lateinit var recentBuildsFragment: RecentBuildsFragment

    init {
        refresh()
        isLoggingOut.value = false
    }

    /**
     * Reset the current fragment and currently selected [Account].
     */
    internal fun refresh() {
        //Re-instantiate fragments
        repoListFragment = RepoListFragment.getInstance()
        recentBuildsFragment = RecentBuildsFragment.getInstance()

        repoListFragment
        //Get the currently selected account.
        currentAccount.value = accountManager.getCurrentAccount()

        //Load all the accounts
        allAccounts.value = accountManager.getAllAccounts()

        //Load the first fragment based on supported features.
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

    /**
     * Set [account] to the current [Account].
     *
     * Note: Application has to reinject the dependency.
     */
    internal fun modelSwitchCurrentAccount(account: Account) {
        accountManager.changeCurrentAccount(account.accountId)
        currentAccount.value = account
        refresh()
    }

    /**
     * Switch to display [RepoListFragment].
     */
    internal fun switchToRepositoryList() {
        currentFragment.value = repoListFragment
        currentTitle.value = application.getString(R.string.title_activity_repo)
    }

    /**
     * Switch to display [RecentBuildsFragment].
     */
    internal fun switchToBuildsList() {
        currentFragment.value = recentBuildsFragment
        currentTitle.value = application.getString(R.string.title_activity_builds_list)
    }

    /**
     * Log out from the currently selected account. [isLoggingOut] will have value true while the
     * application is logging out from the account.
     * Once the account gets logout, [currentAccount] will change and application has to re-inject
     * all the dependencies. If the logout fails, [errorLoggingOut] will contain the string error
     * message to display.
     */
    internal fun logoutCurrentAccount() {
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
                        refresh()
                    }, {
                        errorLoggingOut.value = application.getString(R.string.error_logging_out)
                    })
        }
    }
}
