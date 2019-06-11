package dev.sarquella.studyplanner.ui.app

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.rules.FragmentTestRule
import dev.sarquella.studyplanner.ui.app.calendar.CalendarViewModel
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
import dev.sarquella.studyplanner.ui.app.profile.ProfileViewModel
import dev.sarquella.studyplanner.ui.app.subjects.AddSubjectViewModel
import dev.sarquella.studyplanner.ui.app.subjects.SubjectListAdapter
import dev.sarquella.studyplanner.ui.app.subjects.SubjectsViewModel
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.endsWith
import org.junit.*
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@RunWith(AndroidJUnit4::class)
class AppFragmentTest {

    companion object {

        private val calendarViewModel: CalendarViewModel = mockk(relaxed = true)
        private val classListAdapter: ClassListAdapter = mockk(relaxed = true)
        private val taskListAdapter: TaskListAdapter = mockk(relaxed = true)

        private val subjectsViewModel: SubjectsViewModel = mockk(relaxed = true)
        private val subjectListAdapter: SubjectListAdapter = mockk(relaxed = true)
        private val addSubjectViewModel: AddSubjectViewModel = mockk(relaxed = true)

        private val profileViewModel: ProfileViewModel = mockk(relaxed = true)

        private val koinModule = module {
            viewModel { calendarViewModel }
            viewModel { addSubjectViewModel }
            viewModel { subjectsViewModel }
            viewModel { profileViewModel }
            factory { subjectListAdapter }
            factory { classListAdapter }
            factory { taskListAdapter }
        }

        @BeforeClass
        @JvmStatic
        fun beforeClass() {

            every { addSubjectViewModel.isAddButtonEnabled } returns
                    MutableLiveData<Boolean>().apply { postValue(false) }

            loadKoinModules(koinModule)
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            unloadKoinModules(koinModule)
        }
    }

    @get:Rule
    val fragmentTestRule = FragmentTestRule<AppFragment>()

    private val fragment = AppFragment()

    @Before
    fun beforeEach() {
        fragmentTestRule.setFragment(fragment)
    }

    private fun clickOnTabWithTitle(@StringRes tabTitle: Int) {
        onView(
            allOf(
                hasDescendant(withText(tabTitle)),
                isDescendantOfA(withId(R.id.bottomNavMenu)),
                withClassName(endsWith(BottomNavigationItemView::class.simpleName))
            )
        ).perform(click())
    }

    @Test
    fun checkInitialItemIsCalendarFragment() {
        onView(withId(R.id.fragment_calendar)).check(matches(isDisplayed()))
    }

    @Test
    fun whenCalendarMenuItemClicked_thenCalendarFragmentIsShown() {
        clickOnTabWithTitle(R.string.Calendar)

        onView(withId(R.id.fragment_calendar)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSubjectsMenuItemClicked_thenSubjectsFragmentIsShown() {
        clickOnTabWithTitle(R.string.Subjects)

        onView(withId(R.id.fragment_subjects)).check(matches(isDisplayed()))
    }

    @Test
    fun whenProfileMenuItemClicked_thenProfileFragmentIsShown() {
        clickOnTabWithTitle(R.string.Profile)

        onView(withId(R.id.fragment_profile)).check(matches(isDisplayed()))
    }
}