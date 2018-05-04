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

package com.kevalpatel2106.ci.greenbuild.branchPicker

import android.arch.lifecycle.MutableLiveData
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.Branch
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.BaseViewModel
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.SingleLiveEvent
import com.kevalpatel2106.ci.greenbuild.base.utils.arch.recall
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Keval on 24/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal class BranchPickerViewModel @Inject constructor(
        private val application: BaseApplication,
        private val serverInterface: ServerInterface
) : BaseViewModel() {

    internal val branchList = MutableLiveData<ArrayList<Branch>>()

    internal val errorLoadingList = SingleLiveEvent<String>()

    internal var isLoadingList = MutableLiveData<Boolean>()

    internal var isLoadingFirstTime = MutableLiveData<Boolean>()

    internal var hasModeData = MutableLiveData<Boolean>()

    init {
        hasModeData.value = true
        isLoadingList.value = false
        isLoadingFirstTime.value = false

        branchList.value = ArrayList()
    }

    fun getBranchList(page: Int, repoId: String) {
        serverInterface.getBranches(page, repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoadingList.value = true

                    branchList.value?.let {
                        if (it.isEmpty()) isLoadingFirstTime.value = true
                    }
                }
                .doOnTerminate {
                    isLoadingList.value = false
                    isLoadingFirstTime.value = false
                }
                .map {
                    hasModeData.value = it.hasNext

                    if (page == 1) branchList.value!!.clear()

                    branchList.value!!.addAll(it.list)
                    return@map branchList
                }
                .subscribe({
                    it.recall()

                    if (branchList.value!!.isEmpty())
                        errorLoadingList.value = application.getString(R.string.error_no_branch_found)
                }, {
                    errorLoadingList.value = it.message
                })
    }
}
