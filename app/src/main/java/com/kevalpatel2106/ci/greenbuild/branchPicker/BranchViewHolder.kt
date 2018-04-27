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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Branch
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import kotlinx.android.synthetic.main.row_branch_select.view.*

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class BranchViewHolder private constructor(itemView: View)
    : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup): BranchViewHolder {
            return BranchViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_branch_select, parent, false))
        }
    }

    fun bind(branch: Branch, onClick: (branch: Branch) -> Unit) {
        itemView.row_branch_name_radio.text = branch.name
        itemView.row_branch_name_radio.isChecked = branch.isSelected
        if (branch.isDefault) {
            itemView.row_branch_default_chip.visibility = View.VISIBLE
        } else {
            itemView.row_branch_default_chip.visibility = View.GONE
        }

        itemView.setOnClickListener { onClick.invoke(branch) }
    }
}
