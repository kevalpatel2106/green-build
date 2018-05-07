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

package com.kevalpatel2106.ci.greenbuild.cronList


import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
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
import com.kevalpatel2106.grrenbuild.entities.Cron
import com.kevalpatel2106.greenbuild.utils.alert
import com.kevalpatel2106.ci.greenbuild.base.view.DividerItemDecoration
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.buildList.RepoBuildsListFragment
import com.kevalpatel2106.ci.greenbuild.cronList.addCron.AddCronActivity
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.fragment_cron_list.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class CronListFragment : Fragment(), PageRecyclerViewAdapter.RecyclerViewListener<Cron>, CronListEventListener {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: CronListViewModel

    private lateinit var repoId: String

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cron_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(context!!).getApplicationComponent())
                .build()
                .inject(this@CronListFragment)

        model = ViewModelProviders
                .of(this@CronListFragment, viewModelProvider)
                .get(CronListViewModel::class.java)

        //Set the adapter
        cron_list_rv.layoutManager = LinearLayoutManager(context!!)
        cron_list_rv.adapter = CronListAdapter(
                context = context!!,
                cronList = model.cronList.value!!,
                isDeleteSupported = model.isDeleteCronSupported,
                isRunCronSupported = model.isRunCronSupported,
                listener = this,
                eventListener = this@CronListFragment
        )
        cron_list_rv.itemAnimator = DefaultItemAnimator()
        cron_list_rv.addItemDecoration(DividerItemDecoration(context!!))

        model.cronList.observe(this@CronListFragment, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    cron_list_view_flipper.displayedChild = 0
                    (cron_list_rv.adapter as CronListAdapter).notifyDataSetChanged()
                } else {
                    cron_list_view_flipper.displayedChild = 2
                    crons_error_tv.text = getString(R.string.error_cron_available)
                }
            }
        })

        model.errorLoadingList.observe(this@CronListFragment, Observer {
            it?.let {
                cron_list_view_flipper.displayedChild = 2
                crons_error_tv.text = it
            }
        })

        model.isLoadingFirstTime.observe(this@CronListFragment, Observer {
            it?.let {
                if (it) {
                    cron_list_view_flipper.displayedChild = 1
                }
            }
        })

        model.isLoadingList.observe(this@CronListFragment, Observer {
            it?.let {
                if (!it) {
                    cron_list_refresher.isRefreshing = false
                    (cron_list_rv.adapter as CronListAdapter).onPageLoadComplete()
                }
            }
        })

        model.hasModeData.observe(this@CronListFragment, Observer {
            it?.let { (cron_list_rv.adapter as CronListAdapter).hasNextPage = it }
        })

        cron_list_refresher.setOnRefreshListener {
            cron_list_refresher.isRefreshing = true
            model.loadCronList(repoId, 1)
        }

        with(arguments?.getString(RepoBuildsListFragment.ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")
            repoId = this
        }

        if (model.cronList.value!!.isEmpty()) model.loadCronList(repoId, 1)
    }

    override fun onPageComplete(pos: Int) {
        model.loadCronList(repoId, (pos / ServerInterface.PAGE_SIZE) + 1)
    }

    override fun deleteCron(cron: Cron) {
        alert(title = null,
                message = getString(R.string.delete_cron_title_confirmation_title, cron.branchName),
                func = {
                    positiveButton(R.string.btn_title_delete, {
                        model.deleteCron(cron = cron, repoId = repoId)
                    })
                    negativeButton(android.R.string.cancel)
                    cancelable = false
                }
        )
    }

    override fun runCron(cron: Cron) {
        alert(title = null,
                message = getString(R.string.start_cron_title_confirmation_title),
                func = {
                    positiveButton(R.string.btn_title_start, {
                        model.runCron(cron = cron, repoId = repoId)
                    })
                    negativeButton(android.R.string.cancel)
                    cancelable = false
                }
        )
    }

    internal fun addCron() {
        context?.let {
            AddCronActivity.launch(
                    context = it,
                    fragment = this@CronListFragment,
                    resultCode = NEW_CRON_REQUEST_CODE,
                    repoId = repoId
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            NEW_CRON_REQUEST_CODE -> {
                //Refresh tje list
                if (resultCode == Activity.RESULT_OK) model.loadCronList(repoId, 1)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val NEW_CRON_REQUEST_CODE = 7234
        private const val ARG_REPO_ID = "repo_id"

        internal fun get(repoId: String): CronListFragment {
            val cronListFragment = CronListFragment()
            cronListFragment.retainInstance = true
            cronListFragment.arguments = Bundle().apply {
                putString(ARG_REPO_ID, repoId)
            }

            return cronListFragment
        }
    }
}
