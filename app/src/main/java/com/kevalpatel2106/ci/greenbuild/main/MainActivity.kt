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

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.about.AboutActivity
import com.kevalpatel2106.ci.greenbuild.base.account.Account
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.greenbuild.utils.alert
import com.kevalpatel2106.ci.greenbuild.buildList.RecentBuildsFragment
import com.kevalpatel2106.ci.greenbuild.ciSelector.CiSelectorActivity
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import com.kevalpatel2106.ci.greenbuild.repoList.RepoListFragment
import com.kevalpatel2106.ci.greenbuild.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.drawer_layout.view.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AccountClickListener {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    @Inject
    internal lateinit var compatibilityCheck: CompatibilityCheck

    private lateinit var model: MainActivityViewModel

    private var progressDialog: ProgressDialog? = null

    private lateinit var accountsAdapter: AccountsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this)

        model = ViewModelProviders
                .of(this@MainActivity, viewModelProvider)
                .get(MainActivityViewModel::class.java)

        setNavigationDrawer()

        model.currentFragment.observe(this@MainActivity, Observer {
            it?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, it)
                        .commit()

                if (it is RepoListFragment) {
                    nav_menu_extras.menu.findItem(R.id.menu_drawer_repo_listing).isChecked = true
                } else if (it is RecentBuildsFragment) {
                    nav_menu_extras.menu.findItem(R.id.menu_drawer_builds_listing).isChecked = true
                }
            }
        })
        model.currentTitle.observe(this@MainActivity, Observer {
            it?.let { supportActionBar?.title = it }
        })
        model.reinjectDependency.observe(this@MainActivity, Observer {
            //Recreate the dagger object graph.
            (application as? BaseApplication)?.createAppComponent()
            DaggerDiComponent.builder()
                    .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                    .build()
                    .inject(this)
            model = ViewModelProviders
                    .of(this@MainActivity, viewModelProvider)
                    .get(MainActivityViewModel::class.java)

            model.refresh()
        })
        model.currentAccount.observe(this@MainActivity, Observer {
            if (it != null) {
                //Set the header
                val headerView = nav_menu_holder.getHeaderView(0)
                headerView.drawer_header_profile_iv.text = it.username
                headerView.drawer_header_name_tv.text = it.username
                headerView.drawer_header_account_tv.text = it.serverUrl
                        .replace(getString(R.string.schema_https), "")
                        .replace("api.", "")

                //Refresh the drawer
                refreshDrawer()
            } else {
                //Launch splash
                SplashActivity.launch(this@MainActivity.application)
            }
        })
        model.isLoggingOut.observe(this@MainActivity, Observer {
            it?.let {
                if (it) {
                    progressDialog = ProgressDialog(this@MainActivity).apply {
                        setMessage(getString(R.string.logout_progress_dialog_message))
                        setCancelable(false)
                        isIndeterminate = true
                        show()
                    }
                } else {
                    progressDialog?.cancel()
                }
            }
        })
    }

    private fun setNavigationDrawer() {
        val drawerToggle = ActionBarDrawerToggle(this@MainActivity,
                drawer_layout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawerToggle.syncState()
        drawer_layout.addDrawerListener(drawerToggle)
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
                //Do nothing
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                //Do nothing
            }

            override fun onDrawerClosed(drawerView: View) {
                //Do nothing
            }

            override fun onDrawerOpened(drawerView: View) {
                refreshDrawer()
            }
        })
        nav_menu_extras.setNavigationItemSelectedListener(this@MainActivity)

        //Set the drawer switcher button
        nav_menu_holder.getHeaderView(0).drawer_header_dropdown_iv.setOnClickListener {
            model.isAccountsOpen.value = !model.isAccountsOpen.value!!
        }
        model.isAccountsOpen.observe(this@MainActivity, Observer {
            it?.let {

                //Animate the button
                nav_menu_holder.getHeaderView(0)
                        .drawer_header_dropdown_iv
                        .animate()
                        .rotation(if (!it) 0f else 180f)
                        .setDuration(300)
                        .start()

                //Switch the drawer
                nav_menu_holder.nav_menu_extras.visibility = if (!it) View.VISIBLE else View.GONE
                nav_menu_holder.nav_menu_accounts.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        //set the accounts listing
        accountsAdapter = AccountsAdapter(model.allAccounts.value!!, this)
        with(nav_menu_accounts.nav_accounts_list_rv) {
            this.adapter = accountsAdapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.itemAnimator = DefaultItemAnimator()
        }
        model.allAccounts.observe(this@MainActivity, Observer {
            accountsAdapter.notifyDataSetChanged()
        })

        //Set add accounts button
        nav_menu_accounts.add_account_tv.setOnClickListener {
            CiSelectorActivity.launch(this@MainActivity)
        }
    }

    private fun refreshDrawer() {
        //Refresh the drawer
        nav_menu_extras.menu.findItem(R.id.menu_drawer_repo_listing).isVisible =
                compatibilityCheck.isRepoListingSupported()
        nav_menu_extras.menu.findItem(R.id.menu_drawer_builds_listing).isVisible =
                compatibilityCheck.isRecentBuildsListSupported()
    }

    override fun onAccountClick(account: Account) {
        model.modelSwitchCurrentAccount(account)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (drawer_layout.isDrawerOpen(Gravity.START)) {
                    drawer_layout.closeDrawer(Gravity.START)
                } else {
                    drawer_layout.openDrawer(Gravity.START)
                }
                return false
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_drawer_repo_listing -> model.switchToRepositoryList()
            R.id.menu_drawer_builds_listing -> model.switchToBuildsList()
            R.id.nav_about -> AboutActivity.launch(this@MainActivity)
            R.id.nav_logout -> alert(
                    messageResource = R.string.logout_confirmation_dialog_message,
                    func = {
                        positiveButton(R.string.logout_confirmation_dialog_positive_title, {
                            model.logoutCurrentAccount()
                        })
                        negativeButton(android.R.string.cancel)
                        cancelable = true
                    }
            )
            else -> throw IllegalArgumentException("Invalid id for the navigation drawer.")
        }
        drawer_layout.closeDrawer(Gravity.START)
        return false
    }

    companion object {

        fun launch(context: Context, isNewTask: Boolean = false) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                if (isNewTask) {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            })
        }
    }
}
