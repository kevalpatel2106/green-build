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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.view.DividerItemDecoration
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.activity_ciselector_activty.*
import javax.inject.Inject

class CiSelectorActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: CiSelectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciselector_activty)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_activity_ci_selector)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@CiSelectorActivity)

        model = ViewModelProviders.of(this, viewModelFactory)
                .get(CiSelectorViewModel::class.java)
        model.loadCiServerList(this@CiSelectorActivity)

        ci_selector_list.layoutManager = LinearLayoutManager(this@CiSelectorActivity)
        ci_selector_list.itemAnimator = DefaultItemAnimator()
        ci_selector_list.addItemDecoration(DividerItemDecoration(this@CiSelectorActivity))
        ci_selector_list.adapter = CiSelectorAdapter(this, model.ciServers.value!!)

        model.ciServers.observe(this@CiSelectorActivity, Observer {
            (ci_selector_list.adapter as CiSelectorAdapter).notifyDataSetChanged()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {

        internal fun launch(context: Context) {
            context.startActivity(Intent(context, CiSelectorActivity::class.java))
        }
    }
}
