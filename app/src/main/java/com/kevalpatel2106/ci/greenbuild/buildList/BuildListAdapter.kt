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

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.build.Build

/**
 * Created by Keval on 18/04/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
class BuildListAdapter(private val buildsList: ArrayList<Build>)
    : RecyclerView.Adapter<BuildViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder = BuildViewHolder.create(parent)

    override fun getItemCount(): Int = buildsList.size

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) = holder.bind(buildsList[position])

}
