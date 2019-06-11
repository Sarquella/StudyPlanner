package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.databinding.DialogAddNewClassBinding
import dev.sarquella.studyplanner.helpers.extensions.afterTextChanged
import dev.sarquella.studyplanner.ui.app.subjects.detail.abstractions.FullTransparentDialogFragment
import kotlinx.android.synthetic.main.dialog_add_new_class.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddNewClassDialogFragment private constructor() : FullTransparentDialogFragment() {

    private val viewModel: AddNewClassDialogViewModel by viewModel {
        parametersOf(arguments?.getString("subjectId") ?: "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: DialogAddNewClassBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_new_class, container, false)
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
        etStartTime.afterTextChanged(viewModel::onStartTimeChanged)
        etEndTime.afterTextChanged(viewModel::onEndTimeChanged)

        btAdd.setOnClickListener {
            viewModel.add(
                spClassType.selectedItem.toString(),
                etDay.text.toString(),
                etStartTime.text.toString(),
                etEndTime.text.toString()
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
        fun newInstance(subjectId: String) = AddNewClassDialogFragment().apply {
            arguments = Bundle().apply {
                putString("subjectId", subjectId)
            }
        }
    }

}