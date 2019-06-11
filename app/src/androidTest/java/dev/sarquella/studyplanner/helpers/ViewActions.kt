package dev.sarquella.studyplanner.helpers

import android.view.View
import android.widget.RadioGroup
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import org.hamcrest.Matcher
import java.util.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.PerformException
import androidx.test.espresso.util.TreeIterables
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import java.util.concurrent.TimeoutException


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun setVisibility(visibility: Int): ViewAction =
    object : ViewAction {
        override fun getDescription(): String = "Changes the view's visibility"

        override fun getConstraints(): Matcher<View> = ViewMatchers.isAssignableFrom(View::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            view?.visibility = visibility
        }

    }

fun setExpanded(expanded: Boolean): ViewAction =
    object : ViewAction {
        override fun getDescription(): String = "Changes the fab's expanded state"

        override fun getConstraints(): Matcher<View> = ViewMatchers.isAssignableFrom(FloatingActionButton::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            (view as? FloatingActionButton)?.isExpanded = expanded
        }
    }

fun checkButton(descendantId: Int): ViewAction =
    object : ViewAction {
        override fun getDescription(): String = "Checks a descendant given its id"

        override fun getConstraints(): Matcher<View> = ViewMatchers.isAssignableFrom(RadioGroup::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            (view as? RadioGroup)?.check(descendantId)
        }
    }

fun selectTabAtPosition(position: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription() = "select tab at position $position"

        override fun getConstraints() = ViewMatchers.isAssignableFrom(TabLayout::class.java)

        override fun perform(uiController: UiController, view: View) {
            (view as? TabLayout)?.getTabAt(position)?.let { tab ->
                tab.select()
            }
            uiController.loopMainThreadUntilIdle()
        }
    }
}

fun selectDay(calendarDay: CalendarDay): ViewAction {
    return object : ViewAction {
        override fun getDescription() = "select day $calendarDay"

        override fun getConstraints() = ViewMatchers.isAssignableFrom(MaterialCalendarView::class.java)

        override fun perform(uiController: UiController, view: View) {
            (view as? MaterialCalendarView)?.javaClass
                ?.getDeclaredMethod(
                    "onDateClicked",
                    CalendarDay::class.java,
                    Boolean::class.java
                )?.let { method ->
                    method.isAccessible = true
                    method.invoke(view, calendarDay, true)
                }
        }
    }
}