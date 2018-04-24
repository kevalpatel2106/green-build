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

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.Build
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildState
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.TriggerType
import com.kevalpatel2106.ci.greenbuild.base.utils.ConversationUtils
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorCompat
import com.kevalpatel2106.ci.greenbuild.base.utils.isEmpty
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import kotlinx.android.synthetic.main.row_builds_list.view.*

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class BuildViewHolder private constructor(itemView: View)
    : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup): BuildViewHolder {
            return BuildViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_builds_list, parent, false))
        }
    }

    fun bind(build: Build) {
        itemView.build_status_view.setBackgroundColor(getBuildStateColor(itemView.context, build.state))

        itemView.built_number_tv.text = "#${build.number}"
        itemView.branch_name_tv.text = build.branchName

        itemView.commit_message_tv.text = build.commit.message
        itemView.commit_hash_tv.text = build.commit.sha.drop(build.commit.sha.length - 8 /* Show last 8 letters */)
        itemView.build_trigger_type_iv.setImageResource(getEventTypeImage(build.triggerType))

        itemView.commit_author_name_tv.text = build.author.username
        itemView.commit_author_avatar_iv.setImageResource(R.drawable.ic_user)

        if (build.duration.isEmpty()) {
            itemView.time_taken_tv.text = "-"
        } else {
            itemView.time_taken_tv.text = ConversationUtils
                    .convertToHumanReadableDuration(build.duration * 1000L)
        }

        if (build.startedAt == 0L) {
            itemView.build_start_time_tv.text = "-"
        } else {
            itemView.build_start_time_tv.text = ConversationUtils.getDate(build.startedAt)
        }
    }

    @DrawableRes
    private fun getEventTypeImage(triggerType: TriggerType): Int {
        return when (triggerType) {
            TriggerType.PUSH -> R.drawable.git_commit
            TriggerType.PULL_REQUEST -> R.drawable.git_pull_request
            TriggerType.CRON -> R.drawable.ic_cron
        }
    }

    @ColorInt
    private fun getBuildStateColor(context: Context, buildState: BuildState): Int {
        return when (buildState) {
            BuildState.PASSED -> context.getColorCompat(R.color.build_passed)
            BuildState.RUNNING -> context.getColorCompat(R.color.build_running)
            BuildState.FAILED -> context.getColorCompat(R.color.build_failed)
            BuildState.CANCELED -> context.getColorCompat(R.color.build_canceled)
            BuildState.ERRORED -> context.getColorCompat(R.color.build_errored)
            BuildState.BOOTING -> context.getColorCompat(R.color.build_booting)
        }

    }

}
