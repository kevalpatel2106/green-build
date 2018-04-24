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

package com.kevalpatel2106.ci.greenbuild.cronList.addCron

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import javax.inject.Inject

class AddCronActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: AddCronViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@AddCronActivity)

        model = ViewModelProviders
                .of(this@AddCronActivity, viewModelProvider)
                .get(AddCronViewModel::class.java)

        setContentView(R.layout.activity_add_cron)
    }

    companion object {

        fun launch(context: Context, fragment: Fragment, resultCode: Int) {
            fragment.startActivityForResult(Intent(context, AddCronActivity::class.java), resultCode)
        }

        fun launch(activity: Activity, resultCode: Int) {
            activity.startActivityForResult(Intent(activity, AddCronActivity::class.java), resultCode)
        }
    }
}
