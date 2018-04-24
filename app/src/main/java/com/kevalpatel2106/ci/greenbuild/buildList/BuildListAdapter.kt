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
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Build
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class BuildListAdapter(
        context: Context,
        buildsList: ArrayList<Build>,
        listener: PageRecyclerViewAdapter.RecyclerViewListener<Build>)
    : PageRecyclerViewAdapter<BuildViewHolder, Build>(context, buildsList, listener) {

    override fun bindView(holder: BuildViewHolder, item: Build) {
        holder.bind(item)
    }

    override fun prepareViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        return BuildViewHolder.create(parent)
    }

    override fun prepareViewType(position: Int): Int {
        return 1
    }

    override fun getPageSize(): Int {
        return ServerInterface.PAGE_SIZE
    }
}
