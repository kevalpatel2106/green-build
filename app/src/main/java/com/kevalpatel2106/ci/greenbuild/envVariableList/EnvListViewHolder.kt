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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.envVars.EnvVars
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import kotlinx.android.synthetic.main.row_env_vars.view.*

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class EnvListViewHolder private constructor(
        itemView: View,
        private val isDeleteSupported: Boolean,
        private val isEditPublicSupported: Boolean,
        private val isEditPrivateSupported: Boolean
) : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup,
                   isDeleteSupported: Boolean,
                   isEditPublicSupported: Boolean,
                   isEditPrivateSupported: Boolean
        ): EnvListViewHolder {

            return EnvListViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                            .inflate(R.layout.row_env_vars, parent, false),
                    isDeleteSupported = isDeleteSupported,
                    isEditPublicSupported = isEditPublicSupported,
                    isEditPrivateSupported = isEditPrivateSupported
            )
        }
    }

    fun bind(envVars: EnvVars,
             onEdit: (envVar: EnvVars) -> Unit,
             onDelete: (envVar: EnvVars) -> Unit) {
        itemView.row_env_var_name_tv.text = envVars.name
        itemView.row_env_var_value_tv.text = if (envVars.public) {
            envVars.value
        } else {
            itemView.context.getString(R.string.private_variable_value_mask)
        }
        itemView.row_env_var_private_iv.setImageResource(if (envVars.public)
            R.drawable.ic_public
        else
            R.drawable.ic_private)

        //Set delete button
        if (isDeleteSupported) {
            itemView.row_env_var_delete_btn.visibility = View.VISIBLE
            itemView.row_env_var_delete_btn.displayLoader(envVars.isDeleting)
            itemView.row_env_var_delete_btn.setOnClickListener { onDelete.invoke(envVars) }
        } else {
            itemView.row_env_var_delete_btn.visibility = View.GONE
        }

        //Set edit button
        if (envVars.public) {
            itemView.row_env_var_edit_btn.visibility = if (isEditPublicSupported) View.VISIBLE else View.GONE
        } else {
            itemView.row_env_var_edit_btn.visibility = if (isEditPrivateSupported) View.VISIBLE else View.GONE
        }

        itemView.row_env_var_edit_btn.setOnClickListener { onEdit.invoke(envVars) }
    }
}
