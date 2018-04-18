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
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.utils.showSnack
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.activity_repo_list.*
import javax.inject.Inject

/**
 * An [AppCompatActivity] to display the list of list of user repo.
 */
class RepoListActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@RepoListActivity)

        val model = ViewModelProviders
                .of(this@RepoListActivity, viewModelProvider)
                .get(RepoListViewModel::class.java)

        //Set the adapter
        repo_list_rv.layoutManager = LinearLayoutManager(this@RepoListActivity)
        repo_list_rv.adapter = RepoListAdapter(model.repoList.value!!)
        repo_list_rv.itemAnimator = DefaultItemAnimator()

        model.repoList.observe(this@RepoListActivity, Observer {
            repo_list_rv.adapter.notifyDataSetChanged()
        })

        model.errorLoadingList.observe(this@RepoListActivity, Observer {
            it?.let { showSnack(it) }
        })

        model.loadRepoList()
    }
}
