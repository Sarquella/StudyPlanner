package dev.sarquella.studyplanner.ui.app.calendar.tasks

import android.media.CamcorderProfile.get
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.calendar.CalendarViewModel
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
import kotlinx.android.synthetic.main.fragment_daily_tasks.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class DailyTasksFragment : Fragment() {

    private val viewModel: CalendarViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_daily_tasks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildTasksList()
    }

    private fun buildTasksList() {
        viewModel.tasksList.observe(this, Observer { listBuilder ->
            recyclerView.adapter = get<TaskListAdapter> {
                parametersOf(listBuilder.build(this))
            }
        })
    }

}