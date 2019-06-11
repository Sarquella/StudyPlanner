package dev.sarquella.studyplanner.helpers

import android.graphics.Color
import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.card.MaterialCardView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import dev.sarquella.studyplanner.ui.app.calendar.EventDecorator
import org.hamcrest.Description
import org.hamcrest.Matcher
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.NonNull


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun hasBackgroundColor(color: String?): Matcher<View> =
    object : BoundedMatcher<View, MaterialCardView>(MaterialCardView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("has background color")
        }

        override fun matchesSafely(item: MaterialCardView?): Boolean =
            item?.cardBackgroundColor?.defaultColor == Color.parseColor(color)

    }

fun withTitle(title: String?): Matcher<View> =
    object : BoundedMatcher<View, CollapsingToolbarLayout>(CollapsingToolbarLayout::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("has title")
        }

        override fun matchesSafely(item: CollapsingToolbarLayout?): Boolean =
            item?.title?.equals(title) ?: false

    }

fun withRecyclerView(id: Int) = RecyclerViewMatcher(id)

fun withSelectedDay(selectedDay: CalendarDay): Matcher<View> =
    object : BoundedMatcher<View, MaterialCalendarView>(MaterialCalendarView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("with selected day")
        }

        override fun matchesSafely(item: MaterialCalendarView?): Boolean =
            item?.selectedDate == selectedDay
    }

fun withDecorators(decorators: List<EventDecorator>): Matcher<View> =
    object : BoundedMatcher<View, MaterialCalendarView>(MaterialCalendarView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("with decorators")
        }

        override fun matchesSafely(item: MaterialCalendarView?): Boolean {
            item?.let { calendarView ->
                MaterialCalendarView::class.memberProperties.find { it.name == "dayViewDecorators" }?.let { field ->
                    field.isAccessible = true
                    val currentDecorators = field.get(calendarView) as ArrayList<DayViewDecorator>
                    return currentDecorators == decorators
                }
            }
            return false
        }
    }