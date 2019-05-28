package dev.sarquella.studyplanner.ui.app.calendar

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import dev.sarquella.studyplanner.data.vo.EventGroup


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class EventDecorator(private val eventGroup: EventGroup) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean = eventGroup.day == day

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(EventSpan(eventGroup.colors))
    }

}