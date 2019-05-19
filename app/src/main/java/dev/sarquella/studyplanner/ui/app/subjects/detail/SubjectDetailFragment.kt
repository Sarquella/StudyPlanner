package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.databinding.FragmentSubjectDetailBinding
import kotlinx.android.synthetic.main.fragment_subject_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectDetailFragment : Fragment() {

    private val args: SubjectDetailFragmentArgs by navArgs()

    private val subjectDetailViewModel: SubjectDetailViewModel by viewModel {
        parametersOf(args.subjectId)
    }
    private val addSubjectItemViewModel: AddSubjectItemViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSubjectDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subject_detail, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.subjectDetailViewModel = subjectDetailViewModel
        binding.addSubjectItemViewModel = addSubjectItemViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindObservables()
    }

    private fun bindObservables() {
        bindShowAddItemDialog()
    }

    private fun bindShowAddItemDialog() {
        subjectDetailViewModel.showAddItemDialog.observe(this, Observer { show ->
            if(show)
                btAdd.isExpanded = true
        })
    }
}