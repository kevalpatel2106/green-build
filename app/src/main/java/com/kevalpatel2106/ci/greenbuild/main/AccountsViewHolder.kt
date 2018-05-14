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

package com.kevalpatel2106.ci.greenbuild.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.grrenbuild.entities.Account
import kotlinx.android.synthetic.main.row_account.view.*

/**
 * Created by Keval on 18/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class AccountsViewHolder private constructor(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun create(parent: ViewGroup): AccountsViewHolder {
            return AccountsViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_account, parent, false))
        }
    }

    fun bind(account: Account, onClick: (account: Account) -> Unit) {
        itemView.account_list_name_tv.text = account.username
        itemView.account_list_deomain_tv.text = account.serverUrl
        itemView.account_list_avatar_iv.text = account.username
        itemView.setOnClickListener { onClick.invoke(account) }
    }
}
