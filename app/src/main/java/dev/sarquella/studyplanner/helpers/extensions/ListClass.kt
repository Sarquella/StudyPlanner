package dev.sarquella.studyplanner.helpers.extensions

import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.vo.Event


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */
 
fun List<Class>.retrieveEventList(): List<Event> =
        this.map { Event(it.subjectColor, it.startDate.toCalendarDay()) }