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

import android.app.Activity
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.repo.Repo
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorCompat
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.repoDetail.RepoDetailActivity
import kotlinx.android.synthetic.main.row_repo_list.view.*


/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
internal class RepoViewHolder private constructor(private val activity: Activity, itemView: View)
    : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(activity: Activity, parent: ViewGroup): RepoViewHolder {
            return RepoViewHolder(
                    activity,
                    LayoutInflater.from(parent.context).inflate(R.layout.row_repo_list, parent, false)
            )
        }
    }

    fun bind(repo: Repo) {
        val spannableString = SpannableString("${repo.owner.name}/${repo.name}")
        spannableString.setSpan(
                ForegroundColorSpan(itemView.context.getColorCompat(R.color.colorAccent)),
                repo.owner.name.length + 1,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        itemView.repo_title_tv.text = spannableString

        itemView.repo_description_tv.visibility = if (repo.description != null) View.VISIBLE else View.GONE
        itemView.repo_description_tv.text = repo.description

        itemView.setOnClickListener {
            val pairs = arrayListOf<Pair<View, String>>(
                    Pair.create(itemView.repo_title_tv, ViewCompat.getTransitionName(itemView.repo_title_tv)),
                    Pair.create(itemView.repo_description_tv, ViewCompat.getTransitionName(itemView.repo_description_tv))
            ).toTypedArray()

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    *pairs
            )

            RepoDetailActivity.launch(
                    context = itemView.context,
                    repoId = repo.id,
                    repoName = repo.name,
                    repoDescription = repo.description,
                    repoOwnerName = repo.owner.name,
                    options = options
            )
        }
    }

}
