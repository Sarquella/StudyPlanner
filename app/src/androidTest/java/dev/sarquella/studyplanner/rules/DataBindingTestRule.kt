package dev.sarquella.studyplanner.rules

import androidx.test.espresso.IdlingRegistry
import androidx.test.rule.ActivityTestRule
import dev.sarquella.studyplanner.helpers.DataBindingIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

/**
 * A JUnit rule that registers an idling resource for all fragment views that use data binding.
 */
class DataBindingTestRule(activityTestRule: ActivityTestRule<*>) : TestWatcher() {
    private val idlingResource = DataBindingIdlingResource(activityTestRule)

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

}