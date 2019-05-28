package dev.sarquella.studyplanner.ui.app.calendar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.vo.EventGroup
import dev.sarquella.studyplanner.helpers.selectTabAtPosition
import dev.sarquella.studyplanner.helpers.withDecorators
import dev.sarquella.studyplanner.rules.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@RunWith(AndroidJUnit4::class)
class CalendarFragmentTest {

    companion object {

        private val viewModel: CalendarViewModel = mockk(relaxUnitFun = true)
        private val eventDecorator: EventDecorator = mockk(relaxed = true)

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            loadKoinModules(
                module {
                    viewModel { viewModel }
                    factory { eventDecorator }
                }
            )
        }
    }

    @get:Rule
    val fragmentTestRule = FragmentTestRule<CalendarFragment>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fragment = CalendarFragment()

    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment)
    }

    private val groupedEvents = MutableLiveData<List<EventGroup>>()

    private fun mockViewModel() {
        every { viewModel.groupedEvents } returns groupedEvents
    }

    @Test
    fun whenTabIsSelected_thenViewModelIsNotified() {
        val firstTab = 0
        val secondTab = 1

        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(secondTab))
        verify { viewModel.onTabSelected(secondTab) }

        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(firstTab))
        verify { viewModel.onTabSelected(firstTab) }
    }

    @Test
    fun whenGroupedEventsIsProvided_thenDecoratorsAreAddedToCalendar() {
        val eventGroups = listOf<EventGroup>(mockk(), mockk())
        groupedEvents.postValue(eventGroups)
        val expected = eventGroups.map { eventDecorator }

        onView(withId(R.id.calendarView)).check(matches(withDecorators(expected)))
    }

    @Test
    fun whenNewGroupedEventsIsProvided_thenDecoratorsAreNotAccumulated() {
        groupedEvents.postValue(listOf(mockk(), mockk(), mockk()))

        val eventGroups = listOf<EventGroup>(mockk(), mockk())
        groupedEvents.postValue(eventGroups)

        val expected = eventGroups.map { eventDecorator }

        onView(withId(R.id.calendarView)).check(matches(withDecorators(expected)))
    }
}