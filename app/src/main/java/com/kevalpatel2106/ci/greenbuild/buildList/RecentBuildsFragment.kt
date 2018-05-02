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
import kotlinx.android.synthetic.main.fragment_recent_builds.*
import javax.inject.Inject

/**
 * Created by Keval on 02/05/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class RecentBuildsFragment: Fragment(), PageRecyclerViewAdapter.RecyclerViewListener<Build>{

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: RecentBuildsListViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recent_builds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(context!!).getApplicationComponent())
                .build()
                .inject(this@RecentBuildsFragment)

        model = ViewModelProviders
                .of(this@RecentBuildsFragment, viewModelProvider)
                .get(RecentBuildsListViewModel::class.java)

        //Set the adapter
        recent_builds_list_rv.layoutManager = LinearLayoutManager(context!!)
        recent_builds_list_rv.adapter = BuildListAdapter(
                context = context!!,
                buildsList = model.buildsList.value!!,
                displayRepoInfo = true,
                listener = this
        )
        recent_builds_list_rv.itemAnimator = DefaultItemAnimator()
        recent_builds_list_rv.addItemDecoration(DividerItemDecoration(context!!))

        model.buildsList.observe(this@RecentBuildsFragment, Observer {

            it?.let {
                if (it.isNotEmpty()) {
                    recent_build_list_view_flipper.displayedChild = 0
                    (recent_builds_list_rv.adapter as BuildListAdapter).notifyDataSetChanged()
                } else {
                    recent_build_list_view_flipper.displayedChild = 2
                    recent_builds_error_tv.text = getString(R.string.error_no_build_started)
                }
            }
        })

        model.errorLoadingList.observe(this@RecentBuildsFragment, Observer {
            it?.let {
                recent_build_list_view_flipper.displayedChild = 2
                recent_builds_error_tv.text = it
            }
        })

        model.isLoadingFirstTime.observe(this@RecentBuildsFragment, Observer {
            it?.let {
                if (it) recent_build_list_view_flipper.displayedChild = 1
            }
        })

        model.isLoadingList.observe(this@RecentBuildsFragment, Observer {
            it?.let {
                if (!it) {
                    recent_builds_list_refresher.isRefreshing = false
                    (recent_builds_list_rv.adapter as BuildListAdapter).onPageLoadComplete()
                }
            }
        })

        model.hasModeData.observe(this@RecentBuildsFragment, Observer {
            it?.let { (recent_builds_list_rv.adapter as BuildListAdapter).hasNextPage = it }
        })

        recent_builds_list_refresher.setOnRefreshListener {
            recent_builds_list_refresher.isRefreshing = true
            model.loadRecentBuildsList( 1)
        }

        if (model.buildsList.value!!.isEmpty()) {
            model.loadRecentBuildsList( 1)
        }
    }

    override fun onPageComplete(pos: Int) {
        model.loadRecentBuildsList( (pos / ServerInterface.PAGE_SIZE) + 1)
    }

    override fun onStop() {
        super.onStop()
        (recent_builds_list_rv.adapter as BuildListAdapter).close()
    }

    companion object {

        internal fun getInstance(): RecentBuildsFragment {
            val buildListFragment = RecentBuildsFragment()
            buildListFragment.retainInstance = true
            return buildListFragment
        }
    }
}