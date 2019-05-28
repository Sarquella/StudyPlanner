package dev.sarquella.studyplanner.ui.app.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.helpers.extensions.onTabSelected
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_calendar, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        bindObservables()
    }

    private fun setListeners() {
        tabLayout.onTabSelected(viewModel::onTabSelected)
    }

    private fun bindObservables() {
        bindCalendarEvents()
    }

    private fun bindCalendarEvents() {
        viewModel.groupedEvents.observe(this, Observer { groupedEvents ->
            calendarView.removeDecorators()
            val decorators = groupedEvents.map { eventGroup ->
                get<EventDecorator> {
                    parametersOf(eventGroup)
                }
            }
            calendarView.addDecorators(decorators)
        })
    }

}