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

package com.kevalpatel2106.ci.greenbuild.syncAdapter

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle

/**
 * Created by Keval on 12-Sep-17.
 * Sync adapter to register and sync the account details.
 */

internal class SyncAdapter : AbstractThreadedSyncAdapter {

    /**
     * Public constructor.
     *
     * @param context        Instance
     * @param autoInitialize true if automatically initialized.
     */
    constructor(context: Context, autoInitialize: Boolean) : super(context, autoInitialize)

    /**
     * Public constructor.
     *
     * @param context        Instance
     * @param autoInitialize true if automatically initialized.
     */
    constructor(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean) : super(context, autoInitialize, allowParallelSyncs)

    override fun onPerformSync(account: Account,
                               bundle: Bundle,
                               s: String,
                               contentProviderClient: ContentProviderClient,
                               syncResult: SyncResult) {
        //TODO sync the notification
    }
}
