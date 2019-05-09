package dev.sarquella.studyplanner.ui.app.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.databinding.FragmentSubjectsBinding
import dev.sarquella.studyplanner.helpers.extensions.getSelectedButtonTintHexColor
import kotlinx.android.synthetic.main.dialog_add_subject.*
import kotlinx.android.synthetic.main.fragment_subjects.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectsFragment : Fragment() {

    private val subjectsViewModel: SubjectsViewModel by viewModel()
    private val addSubjectViewModel: AddSubjectViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSubjectsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subjects, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.subjectsViewModel = subjectsViewModel
        binding.addSubjectViewModel = addSubjectViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        bindSubjectsList()
        bindObservables()
    }

    private fun setListeners() {
        btAddSubject.setOnClickListener {
            addSubjectViewModel.add(etName.text.toString(), rgColorSelector.getSelectedButtonTintHexColor())
        }
    }

    private fun bindSubjectsList() {
        recyclerView.adapter = get<SubjectListAdapter> {
            parametersOf(subjectsViewModel.subjectsList.build(this))
        }
    }

    private fun bindObservables() {
        bindShowAddSubjectDialog()
        bindDismissAddSubjectDialog()
    }

    private fun bindShowAddSubjectDialog() {
        subjectsViewModel.showAddSubjectDialog.observe(this, Observer { show ->
            if (show)
                btAdd.isExpanded = true
        })
    }

    private fun bindDismissAddSubjectDialog() {
        addSubjectViewModel.dismiss.observe(this, Observer { dismiss ->
            if (dismiss)
                btAdd.isExpanded = false
        })
    }

}