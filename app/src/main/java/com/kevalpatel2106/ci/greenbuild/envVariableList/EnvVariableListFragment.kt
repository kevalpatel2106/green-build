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

package com.kevalpatel2106.ci.greenbuild.envVariableList


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
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
import com.kevalpatel2106.ci.greenbuild.base.arch.recall
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.envVars.EnvVars
import com.kevalpatel2106.ci.greenbuild.base.utils.alert
import com.kevalpatel2106.ci.greenbuild.base.view.DividerItemDecoration
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.buildList.BuildListFragment
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import com.kevalpatel2106.ci.greenbuild.envVariableList.editVariable.EditVariableDialog
import com.kevalpatel2106.ci.greenbuild.envVariableList.editVariable.VariableEditListener
import kotlinx.android.synthetic.main.fragment_env_variables_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class EnvVariableListFragment : Fragment(),
        PageRecyclerViewAdapter.RecyclerViewListener<EnvVars>,
        EnvVarsListEventListener,
        VariableEditListener {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: EnvVarsListViewModel

    private lateinit var repoId: String

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_env_variables_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(context!!).getApplicationComponent())
                .build()
                .inject(this@EnvVariableListFragment)

        model = ViewModelProviders
                .of(this@EnvVariableListFragment, viewModelProvider)
                .get(EnvVarsListViewModel::class.java)

        //Set the adapter
        env_var_list_rv.layoutManager = LinearLayoutManager(context!!)
        env_var_list_rv.adapter = EnvListAdapter(
                context = context!!,
                list = model.envVarsList.value!!,
                isDeletingSupported = model.isDeleteVariableSupported,
                isEditingPrivateVarsSupported = model.isEditPrivateVariableSupported,
                isEditingPublicVarsSupported = model.isEditPublicVariableSupported,
                pageCompleteListener = this,
                eventListener = this@EnvVariableListFragment
        )
        env_var_list_rv.itemAnimator = DefaultItemAnimator()
        env_var_list_rv.addItemDecoration(DividerItemDecoration(context!!))

        model.envVarsList.observe(this@EnvVariableListFragment, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    env_list_view_flipper.displayedChild = 0
                    (env_var_list_rv.adapter as EnvListAdapter).notifyDataSetChanged()
                } else {
                    env_list_view_flipper.displayedChild = 2
                    env_vars_error_tv.text = getString(R.string.error_no_environment_variable_set)
                }
            }
        })

        model.errorLoadingList.observe(this@EnvVariableListFragment, Observer {
            it?.let {
                env_list_view_flipper.displayedChild = 2
                env_vars_error_tv.text = it
            }
        })

        model.errorDeletingVar.observe(this@EnvVariableListFragment, Observer {
            it?.let { context?.toast(it) }
        })

        model.isLoadingFirstTime.observe(this@EnvVariableListFragment, Observer {
            it?.let {
                if (it) {
                    env_list_view_flipper.displayedChild = 1
                }
            }
        })

        model.isLoadingList.observe(this@EnvVariableListFragment, Observer {
            it?.let {
                if (!it) {
                    env_var_list_refresher.isRefreshing = false
                    (env_var_list_rv.adapter as EnvListAdapter).onPageLoadComplete()
                }
            }
        })

        model.hasModeData.observe(this@EnvVariableListFragment, Observer {
            it?.let { (env_var_list_rv.adapter as EnvListAdapter).hasNextPage = it }
        })

        env_var_list_refresher.setOnRefreshListener {
            env_var_list_refresher.isRefreshing = true
            model.loadEnvVarsList(repoId, 1)
        }

        with(arguments?.getString(BuildListFragment.ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")
            repoId = this
        }

        if (model.envVarsList.value!!.isEmpty()) {
            model.loadEnvVarsList(repoId, 1)
        }
    }

    override fun onPageComplete(pos: Int) {
        model.loadEnvVarsList(repoId, (pos / ServerInterface.PAGE_SIZE) + 1)
    }

    override fun onEdit(envVars: EnvVars) {
        EditVariableDialog.launch(
                fragmentManager = childFragmentManager,
                repoId = repoId,
                envVar = envVars,
                editListener = this@EnvVariableListFragment
        )
    }

    override fun onDelete(envVars: EnvVars) {
        alert(title = null,
                message = getString(R.string.delete_env_variable_title_confirmation_title, envVars.name),
                func = {
                    positiveButton(R.string.btn_title_delete, {
                        model.deleteVariable(envVars = envVars, repoId = repoId)
                    })
                    negativeButton(android.R.string.cancel)
                    cancelable = false
                }
        )
    }

    override fun onEditCompleted(envVars: EnvVars) {
        model.envVarsList.value?.let {
            it[it.indexOf(envVars)] = envVars
            model.envVarsList.recall()
        }
    }

    companion object {

        internal const val ARG_REPO_ID = "repo_id"

        internal fun get(repoId: String): EnvVariableListFragment {
            val envVariableListFragment = EnvVariableListFragment()
            envVariableListFragment.retainInstance = true
            envVariableListFragment.arguments = Bundle().apply {
                putString(ARG_REPO_ID, repoId)
            }

            return envVariableListFragment
        }
    }

}
