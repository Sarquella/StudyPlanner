package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import kotlinx.android.synthetic.main.fragment_classes.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ClassesFragment private constructor() : Fragment() {

    private val viewModel: ClassesViewModel by viewModel {
        parametersOf(arguments?.getString("subjectId") ?: "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_classes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindClassesList()
    }

    private fun bindClassesList() {
        recyclerView.adapter = get<ClassListAdapter> {
            parametersOf(viewModel.classesList.build(this))
        }
    }

    companion object {
        fun newInstance(subjectId: String) = ClassesFragment().apply {
            arguments = Bundle().apply {
                putString("subjectId", subjectId)
            }
        }
    }

}