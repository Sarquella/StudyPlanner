package dev.sarquella.studyplanner.ui.app.calendar.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.calendar.CalendarViewModel
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import kotlinx.android.synthetic.main.fragment_daily_tasks.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class DailyClassesFragment : Fragment() {

    private val viewModel: CalendarViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_daily_classes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindClassesList()
    }

    private fun bindClassesList() {
        viewModel.classesList.observe(this, Observer { listBuilder ->
            recyclerView.adapter = get<ClassListAdapter> {
                parametersOf(listBuilder.build(this))
            }
        })
    }

}