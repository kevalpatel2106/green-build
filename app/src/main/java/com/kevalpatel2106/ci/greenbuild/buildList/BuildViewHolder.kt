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
import com.kevalpatel2106.ci.greenbuild.base.PageRecyclerViewAdapter
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.Build
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.BuildState
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.EventType
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorCompat
import com.kevalpatel2106.ci.greenbuild.base.view.BaseImageView
import com.kevalpatel2106.ci.greenbuild.base.view.BaseTextView
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
class BuildViewHolder(itemView: View) : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup): BuildViewHolder {
            return BuildViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_builds_list, parent, false))
        }
    }

    fun bind(build: Build) {
        itemView.findViewById<View>(R.id.build_status_view)
                .setBackgroundColor(getBuildStateColor(itemView.context, build.state))

        itemView.findViewById<BaseTextView>(R.id.built_number_tv).text = "#${build.number}"
        itemView.findViewById<BaseTextView>(R.id.branch_name_tv).text = build.branchName

        itemView.findViewById<BaseTextView>(R.id.commit_message_tv).text = build.commit.message
        itemView.findViewById<BaseTextView>(R.id.commit_hash_tv).text = build.commit.sha
        itemView.findViewById<BaseImageView>(R.id.build_trigger_type_iv).setImageResource(getEventTypeImage(build.eventType))

        itemView.findViewById<BaseTextView>(R.id.commit_author_name_tv).text = build.author.username
        itemView.findViewById<CircleImageView>(R.id.commit_author_avatar_iv).setImageResource(R.drawable.ic_user)

        itemView.findViewById<BaseTextView>(R.id.time_taken_tv).text = convertToHumanTime(build.duration)
        itemView.findViewById<BaseTextView>(R.id.build_start_time_tv).text = build.startedAt
    }

    @DrawableRes
    private fun getEventTypeImage(eventType: EventType): Int {
        return when (eventType) {
            EventType.PUSH -> R.drawable.git_commit
            EventType.PULL_REQUEST -> R.drawable.git_pull_request
        }
    }

    private fun convertToHumanTime(timeSeconds: Int): String {
        var duration = ""

        with(timeSeconds / 3600) {
            if (this != 0) {
                duration = if (this > 1)
                    duration.plus("$this hours ")
                else
                    duration.plus("$this hour ")
            }
        }

        with((timeSeconds / 60) - (timeSeconds / 3600)) {
            if (this != 0) {
                duration = duration.plus("$this min ")
            }
        }

        with(timeSeconds % 60) {
            if (this != 0) {
                duration = duration.plus("$this sec")
            }
        }

        return duration
    }

    @ColorInt
    private fun getBuildStateColor(context: Context, buildState: BuildState): Int {
        return when (buildState) {
            BuildState.PASSED -> context.getColorCompat(R.color.build_passed)
            BuildState.RUNNING -> context.getColorCompat(R.color.build_running)
            BuildState.FAILED -> context.getColorCompat(R.color.build_failed)
            BuildState.CANCELED -> context.getColorCompat(R.color.build_canceled)
            BuildState.ABORTED -> context.getColorCompat(R.color.build_aborted)
        }

    }

}
