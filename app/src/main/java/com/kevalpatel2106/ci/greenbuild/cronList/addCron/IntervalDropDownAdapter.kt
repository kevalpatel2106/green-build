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

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.utils.getColorCompat

/**
 * Created by Kevalpatel2106 on 25-Apr-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class IntervalDropDownAdapter(
        context: Context,
        private val intervals: ArrayList<String>
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, intervals) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return prepareView(parent, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return prepareView(parent, position)
    }

    private fun prepareView(parent: ViewGroup?, position: Int): View {
        val view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = getItem(position)
        view.findViewById<TextView>(android.R.id.text1)
                .setTextColor(context.getColorCompat(R.color.colorPrimaryText))
        return view
    }

    override fun getItem(position: Int): String = intervals[position]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = intervals.size
}
