package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class TasksFragment private constructor() : Fragment() {

    private val viewModel: TasksViewModel by viewModel {
        parametersOf(arguments?.getString("subjectId") ?: "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_tasks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindClassesList()
    }

    private fun bindClassesList() {
        recyclerView.adapter = get<TaskListAdapter> {
            parametersOf(viewModel.tasksList.build(this))
        }
    }

    companion object {
        fun newInstance(subjectId: String) = TasksFragment().apply {
            arguments = Bundle().apply {
                putString("subjectId", subjectId)
            }
        }
    }

}