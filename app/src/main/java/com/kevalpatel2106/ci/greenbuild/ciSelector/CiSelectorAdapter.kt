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

package com.kevalpatel2106.ci.greenbuild.ciSelector

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.CiServer

/**
 * Created by Keval on 21/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class CiSelectorAdapter(
        private val activity: Activity,
        private val ciServers: ArrayList<CiServer>)
    : RecyclerView.Adapter<CiSelectorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CiSelectorViewHolder {
        return CiSelectorViewHolder.create(activity, parent)
    }

    override fun getItemCount(): Int = ciServers.size

    override fun onBindViewHolder(holder: CiSelectorViewHolder, position: Int) {
        holder.bind(ciServers[position])
    }
}
