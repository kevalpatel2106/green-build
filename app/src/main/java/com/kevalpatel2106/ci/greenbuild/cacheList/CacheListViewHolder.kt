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

package com.kevalpatel2106.ci.greenbuild.cacheList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.cache.Cache
import com.kevalpatel2106.ci.greenbuild.base.utils.ConversationUtils
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter
import kotlinx.android.synthetic.main.row_caches.view.*

/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
internal class CacheListViewHolder private constructor(
        itemView: View,
        private val isDeleteSupported: Boolean
) : PageRecyclerViewAdapter.PageViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup,
                   isDeleteSupported: Boolean
        ): CacheListViewHolder {

            return CacheListViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                            .inflate(R.layout.row_caches, parent, false),
                    isDeleteSupported = isDeleteSupported
            )
        }
    }

    fun bind(cache: Cache, onDeleteClick: () -> Unit) {
        val formattedDate = ConversationUtils.millsToDateFormat(cache.lastModified)
        itemView.cache_name_tv.text = cache.name ?: itemView.context.getString(R.string.cache_prefix, formattedDate)

        itemView.cache_branch_tv.text = cache.branchName
        itemView.cache_last_modified_tv.text = formattedDate
        itemView.cache_size_tv.text = ConversationUtils.humanReadableByteCount(cache.size)

        if (isDeleteSupported) {
            itemView.row_cache_delete_btn.visibility = View.VISIBLE
            itemView.row_cache_delete_btn.displayLoader(cache.isDeleting)
            itemView.row_cache_delete_btn.setOnClickListener { onDeleteClick.invoke() }
        } else {
            itemView.row_cache_delete_btn.visibility = View.GONE
        }
    }
}
