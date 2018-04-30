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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.about.AboutActivity
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.drawer_layout.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: MainActivityViewModel

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
            }
        })
        model.currentTitle.observe(this@MainActivity, Observer {
            it?.let { supportActionBar?.title = it }
        })
        model.openAbout.observe(this@MainActivity, Observer {
            AboutActivity.launch(this@MainActivity)
        })

        model.currentAccount.observe(this@MainActivity, Observer {
            it?.let {
                //Set the header
                val headerView = nav_menu_holder.getHeaderView(0)
                headerView.drawer_header_profile_iv.text = it.username
                headerView.drawer_header_name_tv.text = it.username
                headerView.drawer_header_account_tv.text = it.serverUrl.replace(
                        getString(R.string.schema_https), ""
                ).replace("api.", "")
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

        nav_menu_extras.setNavigationItemSelectedListener(this@MainActivity)
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
     *
     * @return true to display the item as the selected item
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        model.onNavigationItemSelected(item.itemId)
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
