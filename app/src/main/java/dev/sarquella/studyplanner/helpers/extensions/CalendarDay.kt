package dev.sarquella.studyplanner.helpers.extensions

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */
 
fun CalendarDay.toDate(): Date = GregorianCalendar(this.year, this.month - 1, this.day).time