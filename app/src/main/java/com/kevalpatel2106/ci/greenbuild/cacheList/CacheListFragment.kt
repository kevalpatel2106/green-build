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

package com.kevalpatel2106.ci.greenbuild.cacheList


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
import androidx.core.widget.toast
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Cache
import com.kevalpatel2106.ci.greenbuild.base.utils.alert
import com.kevalpatel2106.ci.greenbuild.base.view.DividerItemDecoration
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.buildList.BuildListFragment
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.fragment_cache_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class CacheListFragment : Fragment(), PageRecyclerViewAdapter.RecyclerViewListener<Cache>, CachListEventListener {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: CacheListViewModel

    private lateinit var repoId: String


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cache_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(context!!).getApplicationComponent())
                .build()
                .inject(this@CacheListFragment)

        model = ViewModelProviders
                .of(this@CacheListFragment, viewModelProvider)
                .get(CacheListViewModel::class.java)

        //Set the adapter
        cache_list_rv.layoutManager = LinearLayoutManager(context!!)
        cache_list_rv.adapter = CacheListAdapter(
                context = context!!,
                list = model.cacheList.value!!,
                isDeleteSupported = model.isDeleteVariableSupported,
                pageCompleteListener = this,
                cacheEventListener = this@CacheListFragment
        )
        cache_list_rv.itemAnimator = DefaultItemAnimator()
        cache_list_rv.addItemDecoration(DividerItemDecoration(context!!))

        model.cacheList.observe(this@CacheListFragment, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    cache_list_view_flipper.displayedChild = 0
                    (cache_list_rv.adapter as CacheListAdapter).notifyDataSetChanged()
                } else {
                    cache_list_view_flipper.displayedChild = 2
                    caches_error_tv.text = getString(R.string.error_cache_available)
                }
            }
        })

        model.errorLoadingList.observe(this@CacheListFragment, Observer {
            it?.let {
                cache_list_view_flipper.displayedChild = 2
                caches_error_tv.text = it
            }
        })

        model.errorDeletingCache.observe(this@CacheListFragment, Observer {
            it?.let { context?.toast(it) }
        })

        model.isLoadingFirstTime.observe(this@CacheListFragment, Observer {
            it?.let {
                if (it) {
                    cache_list_view_flipper.displayedChild = 1
                }
            }
        })

        model.isLoadingList.observe(this@CacheListFragment, Observer {
            it?.let {
                if (!it) {
                    cache_list_refresher.isRefreshing = false
                    (cache_list_rv.adapter as CacheListAdapter).onPageLoadComplete()
                }
            }
        })

        model.hasModeData.observe(this@CacheListFragment, Observer {
            it?.let { (cache_list_rv.adapter as CacheListAdapter).hasNextPage = it }
        })

        cache_list_refresher.setOnRefreshListener {
            cache_list_refresher.isRefreshing = true
            model.loadCacheList(repoId, 1)
        }

        with(arguments?.getString(BuildListFragment.ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")
            repoId = this
        }

        if (model.cacheList.value!!.isEmpty()) model.loadCacheList(repoId, 1)
    }

    override fun onPageComplete(pos: Int) {
        model.loadCacheList(repoId, (pos / ServerInterface.PAGE_SIZE) + 1)
    }

    override fun deleteCache(cache: Cache) {
        alert(title = null,
                message = getString(R.string.delete_cache_title_confirmation_title),
                func = {
                    positiveButton(R.string.btn_title_delete, { model.deleteCache(cache) })
                    negativeButton(android.R.string.cancel)
                    cancelable = false
                }
        )
    }

    companion object {

        private const val ARG_REPO_ID = "repo_id"

        internal fun get(repoId: String): CacheListFragment {
            val cacheListFragment = CacheListFragment()
            cacheListFragment.retainInstance = true
            cacheListFragment.arguments = Bundle().apply {
                putString(ARG_REPO_ID, repoId)
            }

            return cacheListFragment
        }
    }
}
