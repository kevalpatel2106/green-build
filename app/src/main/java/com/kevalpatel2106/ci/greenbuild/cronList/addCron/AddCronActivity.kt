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
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.MenuItem
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import androidx.core.widget.toast
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import com.kevalpatel2106.grrenbuild.entities.Branch
import com.kevalpatel2106.greenbuild.utils.showSnack
import com.kevalpatel2106.ci.greenbuild.branchPicker.BranchPickerDialog
import com.kevalpatel2106.ci.greenbuild.branchPicker.BranchPickerListener
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.activity_add_cron.*
import javax.inject.Inject

class AddCronActivity : AppCompatActivity(), BranchPickerListener {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    @Inject
    internal lateinit var compatibilityCheck: CompatibilityCheck

    private lateinit var model: AddCronViewModel

    private lateinit var repoId: String

    private var selectedBranchName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cron)
        setResult(Activity.RESULT_CANCELED)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_activity_add_cron)

        with(intent.getStringExtra(ARG_REPO_ID)) {
            if (this == null)
                throw IllegalArgumentException("No repo id available.")
            repoId = this
        }

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(this).getApplicationComponent())
                .build()
                .inject(this@AddCronActivity)

        model = ViewModelProviders
                .of(this@AddCronActivity, viewModelProvider)
                .get(AddCronViewModel::class.java)

        //Set branch list
        add_cron_branch_drop_down_tv.setOnClickListener {
            BranchPickerDialog.launch(supportFragmentManager, repoId, this)
        }

        //Set interval listing
        model.intervalList.observe(this@AddCronActivity, Observer {
            it?.let {
                add_cron_interval_drop_down_tv.adapter = IntervalDropDownAdapter(
                        this@AddCronActivity,
                        model.intervalList.value!!
                )
            }
        })
        model.errorLoadingInterval.observe(this@AddCronActivity, Observer {
            it?.let { toast(it) }
        })
        model.isLoadingIntervalList.observe(this@AddCronActivity, Observer {
            it?.let { add_cron_interval_drop_down_tv.isEnabled = !it }
        })

        //Set the switch
        dont_run_if_recent_build_exists_switch.isVisible =
                compatibilityCheck.isDontRunIfRecentBuildExistSupported()

        //Set schedule button
        model.invalidInterval.observe(this@AddCronActivity, Observer {
            it?.let { showSnack(it) }
        })
        model.invalidBranch.observe(this@AddCronActivity, Observer {
            it?.let { showSnack(it) }
        })
        model.errorSchedulingCron.observe(this@AddCronActivity, Observer {
            it?.let { showSnack(it) }
        })
        model.scheduledCron.observe(this@AddCronActivity, Observer {
            it?.let {
                setResult(Activity.RESULT_OK)
                showSnack(R.string.schedule_cron_success_message)

                Handler().postDelayed(2300) { finish() }
            }
        })
        model.isSchedulingCron.observe(this@AddCronActivity, Observer {
            it?.let { add_cron_button.displayLoader(it) }
        })
        add_cron_button.setOnClickListener {
            model.scheduleCron(
                    repoId = repoId,
                    branchName = selectedBranchName ?: "",
                    interval = add_cron_interval_drop_down_tv.selectedItem as String,
                    dontRunIfRecentlyBuilt = dont_run_if_recent_build_exists_switch.isChecked
            )
        }

    }

    override fun onBranchSelected(branch: Branch) {
        selectedBranchName = branch.name
        add_cron_branch_drop_down_tv.text = branch.name
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val ARG_REPO_ID = "arg_repo_id"
        fun launch(
                context: Context,
                fragment: Fragment,
                resultCode: Int,
                repoId: String
        ) {
            fragment.startActivityForResult(Intent(context, AddCronActivity::class.java).apply {
                putExtra(ARG_REPO_ID, repoId)
            }, resultCode)
        }

        fun launch(
                activity: Activity,
                resultCode: Int,
                repoId: String
        ) {
            activity.startActivityForResult(Intent(activity, AddCronActivity::class.java).apply {
                putExtra(ARG_REPO_ID, repoId)
            }, resultCode)
        }
    }
}
