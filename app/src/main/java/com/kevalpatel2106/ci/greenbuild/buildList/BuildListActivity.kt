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

package com.kevalpatel2106.ci.greenbuild.buildList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.utils.showSnack
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.activity_build_list.*
import javax.inject.Inject

class BuildListActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: BuildsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_list)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@BuildListActivity)

        model = ViewModelProviders
                .of(this@BuildListActivity, viewModelProvider)
                .get(BuildsListViewModel::class.java)

        //Set the adapter
        builds_list_rv.layoutManager = LinearLayoutManager(this@BuildListActivity)
        builds_list_rv.adapter = BuildListAdapter(model.buildsList.value!!)
        builds_list_rv.itemAnimator = DefaultItemAnimator()

        model.buildsList.observe(this@BuildListActivity, Observer {
            builds_list_rv.adapter.notifyDataSetChanged()
        })

        model.errorLoadingList.observe(this@BuildListActivity, Observer {
            it?.let { showSnack(it) }
        })

        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        with(intent.getStringExtra(ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")

            model.loadBuildsList(this)
        }
    }

    companion object {

        internal const val ARG_REPO_ID = "repo_id"

        fun launch(context: Context, repoId: String) {
            context.startActivity(Intent(context, BuildListActivity::class.java).apply {
                putExtra(ARG_REPO_ID, repoId)
            })
        }
    }
}
