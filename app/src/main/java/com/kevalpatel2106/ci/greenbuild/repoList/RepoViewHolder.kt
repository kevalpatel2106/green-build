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

package com.kevalpatel2106.ci.greenbuild.repoList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.Repo
import com.kevalpatel2106.ci.greenbuild.base.view.BaseTextView

/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup): RepoViewHolder {
            return RepoViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_repo_list, parent, false))
        }
    }

    fun bind(repo: Repo) {
        itemView.findViewById<BaseTextView>(R.id.repo_title_tv).text = repo.name
    }

}