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

package com.kevalpatel2106.ci.greenbuild.repoList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.Repo
import com.kevalpatel2106.ci.greenbuild.base.utils.showSnack
import com.kevalpatel2106.ci.greenbuild.base.view.DividerItemDecoration
import com.kevalpatel2106.ci.greenbuild.buildList.BuildListAdapter
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.activity_build_list.*
import kotlinx.android.synthetic.main.activity_repo_list.*
import javax.inject.Inject

/**
 * An [AppCompatActivity] to display the list of list of user repo.
 */
class RepoListActivity : AppCompatActivity(), PageRecyclerViewAdapter.RecyclerViewListener<Repo> {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: RepoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.app_name)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@RepoListActivity)

        model = ViewModelProviders
                .of(this@RepoListActivity, viewModelProvider)
                .get(RepoListViewModel::class.java)

        //Set the adapter
        val adapter = RepoListAdapter(this@RepoListActivity, model.repoList.value!!, this)
        repo_list_rv.layoutManager = LinearLayoutManager(this@RepoListActivity)
        repo_list_rv.adapter = adapter
        repo_list_rv.itemAnimator = DefaultItemAnimator()
        repo_list_rv.addItemDecoration(DividerItemDecoration(this@RepoListActivity))

        model.repoList.observe(this@RepoListActivity, Observer {
            repo_list_rv.adapter.notifyDataSetChanged()
        })

        model.errorLoadingList.observe(this@RepoListActivity, Observer {
            it?.let { showSnack(it) }
        })

        model.isLoadingFirstTime.observe(this@RepoListActivity, Observer {
            it?.let {
                repo_list_view_flipper.displayedChild = if (it) 1 else 0
            }
        })

        model.isLoadingList.observe(this@RepoListActivity, Observer {
            it?.let {
                if (!it) {
                    repo_list_refresher.isRefreshing = false
                    adapter.onPageLoadComplete()
                }
            }
        })
        model.hasNextPage.observe(this@RepoListActivity, Observer {
            it?.let { adapter.hasNextPage = it }
        })

        repo_list_refresher.setOnRefreshListener {
            repo_list_refresher.isRefreshing = true
            model.loadRepoList(1)
        }

        if (savedInstanceState == null) model.loadRepoList(1)
    }

    override fun onPageComplete(pos: Int) {
        model.loadRepoList((pos / ServerInterface.PAGE_SIZE) + 1)
    }
}
