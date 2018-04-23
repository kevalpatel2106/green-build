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

package com.kevalpatel2106.ci.greenbuild.repoDetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.buildList.BuildListFragment
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.activity_repo_detail.*
import javax.inject.Inject

class RepoDetailActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: RepoDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@RepoDetailActivity)

        model = ViewModelProviders
                .of(this@RepoDetailActivity, viewModelProvider)
                .get(RepoDetailViewModel::class.java)

        val repoId: String = with(intent?.getStringExtra(BuildListFragment.ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")
            return@with this
        }
        model.initializeFragments(repoId)

        setToolbar()
        setBottomNavigation()
        setViewPager()
    }

    /**
     * Set the bottom navigation view.
     */
    private fun setBottomNavigation() {
        //Set up the menu items.
        repo_detail_bottom_navigation.menu
                .findItem(R.id.repo_detail_menu_bottom_build)
                .isVisible = model.isBuildListCompatible
        repo_detail_bottom_navigation.menu
                .findItem(R.id.repo_detail_menu_bottom_env_var)
                .isVisible = model.isEnvVarsListCompatible
        repo_detail_bottom_navigation.menu
                .findItem(R.id.repo_detail_menu_bottom_cron)
                .isVisible = model.isCronListCompatible
        repo_detail_bottom_navigation.menu
                .findItem(R.id.repo_detail_menu_bottom_cache)
                .isVisible = model.isCacheListCompatible

        repo_detail_bottom_navigation.setOnNavigationItemSelectedListener {
            model.changeSelectedItem(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    /**
     * Set up the [RepoDetailPagerAdapter].
     */
    private fun setViewPager() {
        repo_detail_view_pager.adapter = RepoDetailPagerAdapter(model, supportFragmentManager)
        repo_detail_view_pager.setSwipeGestureEnable(false)
        model.selectedItem.observe(this@RepoDetailActivity, Observer {
            it?.let { repo_detail_view_pager.currentItem = it }
        })
    }

    /**
     * Set the [toolbar] for the activity. It will set the name of the repo, owner name, repo image
     * and description of the repo.
     */
    private fun setToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        repo_name_tv.text = intent.getStringExtra(ARG_REPO_NAME) ?: getString(R.string.title_activity_builds)
        repo_owner_name_tv.text = intent.getStringExtra(ARG_REPO_OWNER_NAME)

        repo_image_logo.setImageResource(R.drawable.ic_repo)

        if (intent.getStringExtra(ARG_REPO_DESCRIPTION) != null) {
            repo_description_tv.visibility = View.VISIBLE
            repo_description_tv.text = intent.getStringExtra(ARG_REPO_DESCRIPTION)
        } else {
            repo_description_tv.visibility = View.GONE
        }
    }

    companion object {

        private const val ARG_REPO_ID = "repo_id"
        private const val ARG_REPO_NAME = "repo_name"
        private const val ARG_REPO_DESCRIPTION = "repo_description"
        private const val ARG_REPO_OWNER_NAME = "repo_owner_name"

        internal fun launch(context: Context,
                            repoId: String,
                            repoName: String,
                            repoDescription: String?,
                            repoOwnerName: String?,
                            options: ActivityOptionsCompat? = null) {
            context.startActivity(Intent(context, RepoDetailActivity::class.java).apply {
                putExtra(ARG_REPO_ID, repoId)
                putExtra(ARG_REPO_NAME, repoName)
                putExtra(ARG_REPO_DESCRIPTION, repoDescription)
                putExtra(ARG_REPO_OWNER_NAME, repoOwnerName)
            }, options?.toBundle())
        }
    }
}
