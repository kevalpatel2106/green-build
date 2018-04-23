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
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
internal class EnvListViewHolder private constructor(itemView: View)
    : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup): EnvListViewHolder {
            return EnvListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_env_vars, parent, false))
        }
    }

    fun bind(envVars: EnvVars) {
        itemView.row_env_var_name_tv.text = envVars.name
        itemView.row_env_var_value_tv.text = if (envVars.public) envVars.value else "***************"
        itemView.row_env_var_private_iv.setImageResource(if (envVars.public)
            R.drawable.ic_public
        else
            R.drawable.ic_private)


        itemView.row_env_var_delete_btn.setOnClickListener { }
        itemView.row_env_var_edit_btn.setOnClickListener { }

        //TODO
    }
}
