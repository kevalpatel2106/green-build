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

package com.kevalpatel2106.ci.greenbuild.base.db

import android.app.Application
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import com.kevalpatel2106.grrenbuild.entities.*


/**
 * Created by Kevalpatel2106 on 07-May-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Database(
        entities = [
            Repo::class,
            Build::class,
            EnvVars::class,
            Cron::class,
            Cache::class
        ],
        version = 1,
        exportSchema = true
)
@TypeConverters(BuildStateConverter::class, TriggerTypeConverter::class)
abstract class GBDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "gb_db"
        private var sINSTANCE: GBDatabase? = null

        fun getAppDatabase(application: Application): GBDatabase {
            sINSTANCE?.let {
                sINSTANCE = Room.databaseBuilder(application, GBDatabase::class.java, DB_NAME)
                        .build()
            }
            return sINSTANCE!!
        }

        fun destroyInstance() {
            sINSTANCE = null
        }
    }
}
