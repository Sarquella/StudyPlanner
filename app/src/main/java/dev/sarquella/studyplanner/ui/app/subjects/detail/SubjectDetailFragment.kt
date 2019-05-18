package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.databinding.FragmentSubjectDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectDetailFragment : Fragment() {

    private val args: SubjectDetailFragmentArgs by navArgs()

    private val viewModel: SubjectDetailViewModel by viewModel {
        parametersOf(args.subjectId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSubjectDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subject_detail, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
}