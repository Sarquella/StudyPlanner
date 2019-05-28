package dev.sarquella.studyplanner.helpers.extensions

import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.Event


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun List<Task>.retrieveEventList(): List<Event> =
    this.map { Event(it.subjectColor, it.deliveryDate.toCalendarDay()) }