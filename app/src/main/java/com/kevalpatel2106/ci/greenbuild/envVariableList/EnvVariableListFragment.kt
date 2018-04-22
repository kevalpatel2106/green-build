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


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.R

/**
 * A simple [Fragment] subclass.
 *
 */
class EnvVariableListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_env_variables_list, container, false)
    }

    companion object {

        internal const val ARG_REPO_ID = "repo_id"

        internal fun get(repoId: String): EnvVariableListFragment {
            val envVariableListFragment = EnvVariableListFragment()
            envVariableListFragment.retainInstance = true
            envVariableListFragment.arguments = Bundle().apply {
                putString(ARG_REPO_ID, repoId)
            }

            return envVariableListFragment
        }
    }

}
