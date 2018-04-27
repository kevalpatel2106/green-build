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

package com.kevalpatel2106.ci.greenbuild.branchPicker

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.toast
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Branch
import com.kevalpatel2106.ci.greenbuild.base.view.GBDialogFragment
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.dialog_branch_picker.*
import kotlinx.android.synthetic.main.fragment_build_list.*
import javax.inject.Inject

/**
 * Created by Keval on 24/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class BranchPickerDialog : GBDialogFragment(), PageRecyclerViewAdapter.RecyclerViewListener<Branch> {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: BranchPickerViewModel

    private lateinit var listener: BranchPickerListener

    private lateinit var repoId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Validate the arguments
        if (arguments == null
                || arguments!!.getSerializable(ARG_BRANCH_SELECT_LISTENER) == null) {
            throw IllegalArgumentException("No arguments passed.")
        }

        with(arguments?.getString(ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")
            repoId = this
        }

        listener = arguments!!.getSerializable(ARG_BRANCH_SELECT_LISTENER) as BranchPickerListener

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_branch_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(context!!).getApplicationComponent())
                .build()
                .inject(this@BranchPickerDialog)

        model = ViewModelProviders
                .of(this@BranchPickerDialog, viewModelProvider)
                .get(BranchPickerViewModel::class.java)

        //Set the negative button
        branch_picker_cancel_btn.setOnClickListener { dismiss() }

        //Set positive button
        branch_picker_select_btn.setOnClickListener {
            val selectedName = (branch_picker_list.adapter as BranchSelectAdapter).selectedName

            model.branchList.value!!
                    .filter { selectedName == it.name }
                    .forEach {
                        listener.onBranchSelected(it)
                        dismiss()
                        return@forEach
                    }
        }

        //Set the adapter
        branch_picker_list.layoutManager = LinearLayoutManager(context!!)
        branch_picker_list.adapter = BranchSelectAdapter(
                context = context!!,
                branchList = model.branchList.value!!,
                listener = this
        )
        branch_picker_list.itemAnimator = DefaultItemAnimator()

        model.branchList.observe(this@BranchPickerDialog, Observer {
            it?.let {
                branch_picker_view_flipper.displayedChild = 0
                (branch_picker_list.adapter as BranchSelectAdapter).notifyDataSetChanged()

                if (it.isNotEmpty()) {
                    (branch_picker_list.adapter as BranchSelectAdapter).selectedName = it[0].name
                    listener.onBranchSelected(it[0])
                }
            }
        })

        model.errorLoadingList.observe(this@BranchPickerDialog, Observer {
            it?.let { context?.toast(it) }
            dismiss()
        })

        model.isLoadingFirstTime.observe(this@BranchPickerDialog, Observer {
            it?.let { if (it) branch_picker_view_flipper.displayedChild = 1 }
        })

        model.isLoadingList.observe(this@BranchPickerDialog, Observer {
            it?.let {
                if (!it) {
                    branch_list_refresher.isRefreshing = false
                    (branch_picker_list.adapter as BranchSelectAdapter).onPageLoadComplete()
                }
            }
        })

        model.hasModeData.observe(this@BranchPickerDialog, Observer {
            it?.let { (branch_picker_list.adapter as BranchSelectAdapter).hasNextPage = it }
        })

        branch_list_refresher.setOnRefreshListener {
            builds_list_refresher.isRefreshing = true
            model.getBranchList(repoId = repoId, page = 1)
        }

        if (model.branchList.value!!.isEmpty()) {
            model.getBranchList(repoId = repoId, page = 1)
        }
    }

    override fun onPageComplete(pos: Int) {
        model.getBranchList(repoId = repoId, page = (pos / ServerInterface.PAGE_SIZE) + 1)
    }

    override fun fullHeight() = true

    override fun fullWidth() = true

    companion object {
        private const val ARG_BRANCH_SELECT_LISTENER = "arg_branch_select_listener"
        private const val ARG_REPO_ID = "arg_repo_id"

        internal fun launch(fragmentManager: FragmentManager,
                            repoId: String,
                            listener: BranchPickerListener) {
            val dialog = BranchPickerDialog()
            dialog.retainInstance = true
            dialog.isCancelable = false
            dialog.arguments = Bundle().apply {
                putSerializable(ARG_BRANCH_SELECT_LISTENER, listener)
                putSerializable(ARG_REPO_ID, repoId)
            }

            dialog.show(fragmentManager, BranchPickerDialog::class.java.simpleName)
        }
    }
}
