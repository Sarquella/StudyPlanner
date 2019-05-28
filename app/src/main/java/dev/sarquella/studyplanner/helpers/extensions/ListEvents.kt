package dev.sarquella.studyplanner.helpers.extensions

import android.graphics.Color
import dev.sarquella.studyplanner.data.vo.Event
import dev.sarquella.studyplanner.data.vo.EventGroup


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun List<Event>.toGroupList(): List<EventGroup> {
    val eventGroups = mutableListOf<EventGroup>()
    val groups = this.groupBy { it.day }
    for ((day, eventsInGroup) in groups) {
        eventGroups.add(EventGroup(day, eventsInGroup.map { Color.parseColor(it.color) }))
    }
    return eventGroups
}
