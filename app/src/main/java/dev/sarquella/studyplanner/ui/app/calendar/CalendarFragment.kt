package dev.sarquella.studyplanner.ui.app.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.sarquella.studyplanner.R


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class CalendarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_calendar, container, false)

}