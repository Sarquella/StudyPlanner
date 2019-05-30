package dev.sarquella.studyplanner.ui.app.calendar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prolificinteractive.materialcalendarview.CalendarDay
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.vo.EventGroup
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.helpers.extensions.toCalendarDay
import dev.sarquella.studyplanner.helpers.extensions.toDate
import dev.sarquella.studyplanner.helpers.selectDay
import dev.sarquella.studyplanner.helpers.selectTabAtPosition
import dev.sarquella.studyplanner.helpers.withDecorators
import dev.sarquella.studyplanner.helpers.withSelectedDay
import dev.sarquella.studyplanner.rules.FragmentTestRule
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
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
import java.util.*


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

            val classListAdapter: ClassListAdapter = mockk(relaxed = true)
            val taskListAdapter: TaskListAdapter = mockk(relaxed = true)

            loadKoinModules(
                module {
                    viewModel { viewModel }
                    factory { eventDecorator }
                    factory { classListAdapter }
                    factory { taskListAdapter }
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
    private val classesList = MutableLiveData<ListBuilder<Class>>()
    private val tasksList = MutableLiveData<ListBuilder<Task>>()

    private fun mockViewModel() {
        every { viewModel.groupedEvents } returns groupedEvents
        every { viewModel.classesList } returns classesList
        every { viewModel.tasksList } returns tasksList
    }

    @Test
    fun checkCurrentDateIsSelectedInCalendarInitially() {
        onView(withId(R.id.calendarView)).check(matches(withSelectedDay(Date().toCalendarDay())))
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
    fun whenFirstTabIsSelected_thenDailyClassesFragmentIsDisplayed() {
        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(0))

        onView(withId(R.id.fragment_daily_classes)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSecondTabIsSelected_thenDailyTasksFragmentIsDisplayed() {
        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(1))

        onView(withId(R.id.fragment_daily_tasks)).check(matches(isDisplayed()))
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

    @Test
    fun whenDateIsSelected_thenViewModelIsNotified() {
        val selectedDay = CalendarDay.from(2019, 6, 10)
        onView(withId(R.id.calendarView)).perform(selectDay(selectedDay))

        verify { viewModel.onDateSelected(selectedDay.toDate()) }
    }
}