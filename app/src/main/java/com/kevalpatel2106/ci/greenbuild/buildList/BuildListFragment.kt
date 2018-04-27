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
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Build
import com.kevalpatel2106.ci.greenbuild.base.view.DividerItemDecoration
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.fragment_build_list.*
import javax.inject.Inject

class BuildListFragment : Fragment(), PageRecyclerViewAdapter.RecyclerViewListener<Build> {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: BuildsListViewModel

    private lateinit var repoId: String


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_build_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(context!!).getApplicationComponent())
                .build()
                .inject(this@BuildListFragment)

        model = ViewModelProviders
                .of(this@BuildListFragment, viewModelProvider)
                .get(BuildsListViewModel::class.java)

        //Set the adapter
        builds_list_rv.layoutManager = LinearLayoutManager(context!!)
        builds_list_rv.adapter = BuildListAdapter(
                context = context!!,
                buildsList = model.buildsList.value!!,
                listener = this
        )
        builds_list_rv.itemAnimator = DefaultItemAnimator()
        builds_list_rv.addItemDecoration(DividerItemDecoration(context!!))

        model.buildsList.observe(this@BuildListFragment, Observer {

            it?.let {
                if (it.isNotEmpty()) {
                    build_list_view_flipper.displayedChild = 0
                    (builds_list_rv.adapter as BuildListAdapter).notifyDataSetChanged()
                } else {
                    build_list_view_flipper.displayedChild = 2
                    builds_error_tv.text = getString(R.string.error_no_build_started)
                }
            }
        })

        model.errorLoadingList.observe(this@BuildListFragment, Observer {
            it?.let {
                build_list_view_flipper.displayedChild = 2
                builds_error_tv.text = it
            }
        })

        model.isLoadingFirstTime.observe(this@BuildListFragment, Observer {
            it?.let {
                if (it) build_list_view_flipper.displayedChild = 1
            }
        })

        model.isLoadingList.observe(this@BuildListFragment, Observer {
            it?.let {
                if (!it) {
                    builds_list_refresher.isRefreshing = false
                    (builds_list_rv.adapter as BuildListAdapter).onPageLoadComplete()
                }
            }
        })

        model.hasModeData.observe(this@BuildListFragment, Observer {
            it?.let { (builds_list_rv.adapter as BuildListAdapter).hasNextPage = it }
        })

        builds_list_refresher.setOnRefreshListener {
            builds_list_refresher.isRefreshing = true
            model.loadBuildsList(repoId, 1)
        }

        with(arguments?.getString(ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")
            repoId = this
        }

        if (model.buildsList.value!!.isEmpty()) {
            model.loadBuildsList(repoId, 1)
        }
    }

    override fun onPageComplete(pos: Int) {
        model.loadBuildsList(repoId, (pos / ServerInterface.PAGE_SIZE) + 1)
    }

    override fun onStop() {
        super.onStop()
        (builds_list_rv.adapter as BuildListAdapter).close()
    }

    companion object {

        internal const val ARG_REPO_ID = "repo_id"

        internal fun get(repoId: String): BuildListFragment {
            val buildListFragment = BuildListFragment()
            buildListFragment.retainInstance = true
            buildListFragment.arguments = Bundle().apply {
                putString(ARG_REPO_ID, repoId)
            }

            return buildListFragment
        }
    }
}
