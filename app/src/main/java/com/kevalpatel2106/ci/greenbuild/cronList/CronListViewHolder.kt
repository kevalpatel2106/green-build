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

package com.kevalpatel2106.ci.greenbuild.cronList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Cron
import com.kevalpatel2106.ci.greenbuild.base.utils.ConversationUtils
import com.kevalpatel2106.ci.greenbuild.base.utils.isEmpty
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import kotlinx.android.synthetic.main.row_cron.view.*

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class CronListViewHolder private constructor(
        itemView: View,
        private val isDeleteSupported: Boolean,
        private val isInitiateManuallySupported: Boolean
) : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup,
                   isDeleteSupported: Boolean,
                   isInitiateManuallySupported : Boolean
        ): CronListViewHolder {

            return CronListViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                            .inflate(R.layout.row_cron, parent, false),
                    isDeleteSupported = isDeleteSupported,
                    isInitiateManuallySupported = isInitiateManuallySupported
            )
        }
    }

    fun bind(cron: Cron, onDeleteClick: () -> Unit, onRunClick: () -> Unit) {
        @SuppressLint("SetTextI18n")
        itemView.row_cron_id_tv.text = "#${cron.id}"

        itemView.row_cron_branch_tv.text = cron.branchName

        itemView.row_cron_last_run_time_tv.text = if (cron.lastRun.isEmpty()) {
            itemView.context.getString(R.string.cron_last_ran_never)
        } else {
            itemView.context.getString(R.string.cron_last_ran_before,
                    ConversationUtils.convertToHumanReadableDuration(System.currentTimeMillis() - cron.lastRun))
        }

        itemView.row_cron_next_run_tv.text = if (cron.nextRun.isEmpty()) {
            itemView.context.getString(R.string.cron_next_run_never)
        } else {
            itemView.context.getString(R.string.cron_next_run_after,
                    ConversationUtils.convertToHumanReadableDuration(cron.nextRun - System.currentTimeMillis()))
        }

        if (isDeleteSupported && cron.canIDelete) {
            itemView.row_cron_delete_btn.isVisible = true
            itemView.row_cron_delete_btn.displayLoader(cron.isDeleting)
            itemView.row_cron_delete_btn.setOnClickListener { onDeleteClick.invoke() }
        } else {
            itemView.row_cron_delete_btn.isGone = true
        }

        if (isInitiateManuallySupported && cron.canIStartCron) {
            itemView.row_cron_run_btn.isVisible = true
            itemView.row_cron_run_btn.displayLoader(cron.isStartingCron)
            itemView.row_cron_run_btn.setOnClickListener { onRunClick.invoke() }
        } else {
            itemView.row_cron_run_btn.isGone = true
        }
    }
}
