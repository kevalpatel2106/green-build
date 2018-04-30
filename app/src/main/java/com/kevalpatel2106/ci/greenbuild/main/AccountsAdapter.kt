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
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.base.account.Account

/**
 * Created by Kevalpatel2106 on 30-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class AccountsAdapter(private val accounts: ArrayList<Account>,
                               private val listener: AccountClickListener) : RecyclerView.Adapter<AccountsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        return AccountsViewHolder.create(parent)
    }

    override fun getItemCount(): Int = accounts.size

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        holder.bind(accounts[position], { listener.onAccountClick(it) })
    }

}
