package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.subjects.detail.abstractions.FullTransparentDialogFragment
import dev.sarquella.studyplanner.databinding.DialogAddNewTaskBinding
import dev.sarquella.studyplanner.helpers.extensions.afterTextChanged
import kotlinx.android.synthetic.main.dialog_add_new_class.etDay
import kotlinx.android.synthetic.main.dialog_add_new_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddNewTaskDialogFragment private constructor() : FullTransparentDialogFragment() {

    private val viewModel: AddNewTaskDialogViewModel by viewModel {
        parametersOf(arguments?.getString("subjectId") ?: "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: DialogAddNewTaskBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_new_task, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        bindDismiss()
    }

    private fun setListeners() {
        etDay.afterTextChanged(viewModel::onDayChanged)
        etTime.afterTextChanged(viewModel::onTimeChanged)

        btAdd.setOnClickListener {
            viewModel.add(
                etName.text.toString(),
                spTaskType.selectedItem.toString(),
                etDay.text.toString(),
                etTime.text.toString()
            )
        }
    }

    private fun bindDismiss() {
        viewModel.dismiss.observe(this, Observer { dismiss ->
            if (dismiss)
                this.dismiss()
        })
    }

    companion object {
        fun newInstance(subjectId: String) = AddNewTaskDialogFragment().apply {
            arguments = Bundle().apply {
                putString("subjectId", subjectId)
            }
        }
    }

}