package dev.sarquella.studyplanner.helpers

import android.view.View
import android.widget.RadioGroup
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.Matcher


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