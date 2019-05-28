package dev.sarquella.studyplanner.helpers.extensions

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.time.Month
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun Date.toCalendarDay(): CalendarDay {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return CalendarDay.from(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}