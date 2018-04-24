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

import android.content.Context
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.envVars.EnvVars
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter

/**
 * Created by Keval on 18/04/18.
 * [PageRecyclerViewAdapter] to display the list of [EnvVars]. This list will allow user to edit/delete
 * the [EnvVars]. This is paginated list.
 *
 * @param context Instance of the caller.
 * @param list [ArrayList] of the [EnvVars] to display.
 * @param isDeletingSupported True if the current CI platform supports deleting the environment variables.
 * Based on this value, UI will show/hide delete button.
 * @param isEditingPrivateVarsSupported True if the current CI platform supports editing the private
 * /secrete environment variables. Based on this value, UI will show/hide edit button for private
 * variables.
 * @param isEditingPublicVarsSupported True if the current CI platform supports editing the public
 * environment variables. Based on this value, UI will show/hide edit button for public variables.
 * @param eventListener [EnvVarsListEventListener] to notify caller when any [EnvVars] should be edited
 * or deleted.
 * @param pageCompleteListener [PageRecyclerViewAdapter.RecyclerViewListener] to notify caller when
 * the page ends.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class EnvListAdapter(
        context: Context,
        list: ArrayList<EnvVars>,
        private val isDeletingSupported: Boolean,
        private val isEditingPublicVarsSupported: Boolean,
        private val isEditingPrivateVarsSupported: Boolean,
        pageCompleteListener: PageRecyclerViewAdapter.RecyclerViewListener<EnvVars>,
        private val eventListener: EnvVarsListEventListener)
    : PageRecyclerViewAdapter<EnvListViewHolder, EnvVars>(context, list, pageCompleteListener) {

    override fun bindView(holder: EnvListViewHolder, item: EnvVars) =
            holder.bind(item, { eventListener.onEdit(it) }, { eventListener.onDelete(it) })

    override fun prepareViewHolder(parent: ViewGroup, viewType: Int): EnvListViewHolder {
        return EnvListViewHolder.create(
                parent = parent,
                isDeleteSupported = isDeletingSupported,
                isEditPublicSupported = isEditingPublicVarsSupported,
                isEditPrivateSupported = isEditingPrivateVarsSupported
        )
    }

    override fun prepareViewType(position: Int): Int = 1

    override fun getPageSize(): Int = ServerInterface.PAGE_SIZE
}
