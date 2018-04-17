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

package com.kevalpatel2106.ci.greenbuild.base.account

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by Keval on 12-Sep-17.
 */

internal class SyncService : Service() {


    override fun onCreate() {
        // SyncAdapter is not Thread-safe
        synchronized(LOCK) {
            // Instantiate our SyncAdapter
            syncAdapter = SyncAdapter(this, false)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        // Return our SyncAdapter's IBinder
        return syncAdapter!!.syncAdapterBinder
    }

    companion object {
        /**
         * Lock use to synchronize instantiation of SyncAdapter.
         */
        private val LOCK = Any()
        private var syncAdapter: SyncAdapter? = null
    }
}
