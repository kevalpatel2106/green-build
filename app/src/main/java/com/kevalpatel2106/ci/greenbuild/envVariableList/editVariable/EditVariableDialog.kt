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

package com.kevalpatel2106.ci.greenbuild.envVariableList.editVariable


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*
import androidx.core.widget.toast
import com.kevalpatel2106.ci.greenbuild.R
import com.kevalpatel2106.ci.greenbuild.base.application.BaseApplication
import com.kevalpatel2106.ci.greenbuild.base.ciInterface.entities.EnvVars
import com.kevalpatel2106.ci.greenbuild.di.DaggerDiComponent
import kotlinx.android.synthetic.main.dialog_edit_variable.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class EditVariableDialog : DialogFragment() {

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var model: EditVariableViewModel

    private lateinit var repoId: String

    private lateinit var envVarId: String

    private var editListener: VariableEditListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Validate the arguments
        if (arguments == null
                || arguments!!.getString(ARG_REPO_ID).isNullOrEmpty()
                || arguments!!.getString(ARG_ID).isNullOrEmpty()) {
            throw IllegalArgumentException("Invalid repo id ${arguments!!.getString(ARG_REPO_ID)}" +
                    "or invalid environment variable id ${arguments!!.getString(ARG_ID)}")
        }

        repoId = arguments!!.getString(ARG_REPO_ID)
        envVarId = arguments!!.getString(ARG_ID)

        editListener = arguments!!.getSerializable(ARG_EDIT_LISTENER) as VariableEditListener
        arguments!!.remove(ARG_EDIT_LISTENER)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_edit_variable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerDiComponent.builder()
                .applicationComponent(BaseApplication.get(context!!).getApplicationComponent())
                .build()
                .inject(this@EditVariableDialog)

        model = ViewModelProviders
                .of(this@EditVariableDialog, viewModelProvider)
                .get(EditVariableViewModel::class.java)

        edit_variable_name_et.setText(arguments?.getString(ARG_NAME))
        edit_variable_value_et.setText(arguments?.getString(ARG_VALUE))

        //Set the switch
        edit_variable_is_private_switch.isChecked = arguments!!.getBoolean(ARG_IS_PUBLIC)
        edit_variable_is_private_switch.isEnabled = model.isEditPrivateVariableSupported && model.isEditPublicVariableSupported

        cancel_variable_btn.setOnClickListener { dismiss() }

        save_variable_btn.setOnClickListener {
            model.editVariable(
                    newName = edit_variable_name_et.text!!.trim().toString(),
                    newValue = edit_variable_value_et.text!!.trim().toString(),
                    isPublic = edit_variable_is_private_switch.isChecked,
                    envVarId = envVarId,
                    repoId = repoId
            )
        }

        model.errorEditingVar.observe(this@EditVariableDialog, Observer {
            it?.let { context!!.toast(it) }
        })

        model.isEmptyName.observe(this@EditVariableDialog, Observer {
            it?.let { edit_variable_name_til.error = it }
        })

        model.isEmptyValue.observe(this@EditVariableDialog, Observer {
            it?.let { edit_variable_value_til.error = it }
        })

        model.isSavingVariable.observe(this@EditVariableDialog, Observer {
            it?.let { save_variable_btn.displayLoader(it) }
        })

        model.variableEditSuccess.observe(this@EditVariableDialog, Observer {
            it?.let {
                editListener?.onEditCompleted(it)
                context?.toast(R.string.message_variable_edit_success)
                dismiss()
            }
        })
    }

    /**
     * Make the dialog at least 90% of the screen width. The height will be wrap content. Also apply
     * [R.style.DialogAnimation] to the dialog.
     */
    override fun onStart() {
        super.onStart()
        val window = dialog.window ?: return

        //Display the dialog full width of the screen
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        //Display the at the bottom of the screen
        val wlp = window.attributes
        wlp.gravity = Gravity.CENTER
        wlp.windowAnimations = R.style.DialogAnimation
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
    }

    /**
     * This code is to stop the dialog from being dismissed on rotation, due to a
     * [bug](https://code.google.com/p/android/issues/detail?id=17423) with the compatibility library.
     *
     * See: [POST](https://stackoverflow.com/a/15444485/4690731)
     */
    override fun onDestroyView() {
        val dialog = dialog
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }

    companion object {

        private const val ARG_IS_PUBLIC = "arg_is_public"
        private const val ARG_NAME = "arg_name"
        private const val ARG_VALUE = "arg_value"
        private const val ARG_ID = "arg_id"
        private const val ARG_REPO_ID = "arg_repo_id"
        private const val ARG_EDIT_LISTENER = "arg_edit_listener"

        internal fun launch(fragmentManager: FragmentManager,
                            repoId: String,
                            envVar: EnvVars,
                            editListener: VariableEditListener?): EditVariableDialog {
            val dialog = EditVariableDialog()
            dialog.retainInstance = true
            dialog.isCancelable = true

            dialog.arguments = Bundle().apply {
                putString(ARG_ID, envVar.id)
                putString(ARG_NAME, envVar.name)
                putString(ARG_VALUE, envVar.value)
                putBoolean(ARG_IS_PUBLIC, envVar.public)
                putString(ARG_REPO_ID, repoId)
                putSerializable(ARG_EDIT_LISTENER, editListener)
            }
            dialog.show(fragmentManager, EditVariableDialog::class.java.name)

            return dialog
        }
    }

}
