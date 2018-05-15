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

package com.kevalpatel2106.ci.greenbuild.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kevalpatel2106 on 04-May-18.
 * Bin to handle the references for all the [Scheduler]. This bin is initialized by dagger when application
 * starts and injects by using dagger [com.kevalpatel2106.ci.greenbuild.di.DiModule.provideRxSchedulers].
 *
 * @constructor Public constructor.
 * @param database [Scheduler] for the database queries. Make sure the database operations are single
 * threaded. Default value is [Schedulers.single].
 * @param disk [Scheduler] for the disk operations. Default value is [Schedulers.io].
 * @param main Android main thread [Scheduler]. Default value is [AndroidSchedulers.mainThread].
 * @param compute [Scheduler] to perform the heavy computation. If your work deal with any i/o consider
 * using [network],[database] or [disk] scheduler. Default value is [Schedulers.computation].
 * @param network [Scheduler] to perform network calls. Default value is [Schedulers.io].
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
data class GBRxSchedulers(
        val database: Scheduler = Schedulers.single(),
        val disk: Scheduler = Schedulers.io(),
        val network: Scheduler = Schedulers.io(),
        val compute: Scheduler = Schedulers.computation(),
        val main: Scheduler = AndroidSchedulers.mainThread()
)
