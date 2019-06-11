package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.helpers.selectTabAtPosition
import dev.sarquella.studyplanner.helpers.withTitle
import dev.sarquella.studyplanner.rules.DataBindingTestRule
import dev.sarquella.studyplanner.rules.FragmentTestRule
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import dev.sarquella.studyplanner.ui.app.subjects.detail.classes.ClassesViewModel
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
import dev.sarquella.studyplanner.ui.app.subjects.detail.tasks.TasksViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers
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
class SubjectDetailFragmentTest {

    companion object {

        private val viewModel: SubjectDetailViewModel = mockk(relaxUnitFun = true)

        private val addSubjectItemViewModel: AddSubjectItemViewModel = mockk(relaxed = true)
        private val classesViewModel: ClassesViewModel = mockk(relaxed = true)
        private val classListAdapter: ClassListAdapter = mockk(relaxed = true)
        private val tasksViewModel: TasksViewModel = mockk(relaxed = true)
        private val taskListAdapter: TaskListAdapter = mockk(relaxed = true)
        private val koinModule = module {
            viewModel { viewModel }
            viewModel { addSubjectItemViewModel }
            viewModel { classesViewModel }
            viewModel { tasksViewModel }
            factory { classListAdapter }
            factory { taskListAdapter }
        }

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            loadKoinModules(koinModule)
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            unloadKoinModules(koinModule)
        }
    }

    @get:Rule
    val fragmentTestRule = FragmentTestRule<SubjectDetailFragment>()

    @get:Rule
    val dataBindingTestRule = DataBindingTestRule(fragmentTestRule)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fragment: SubjectDetailFragment = SubjectDetailFragment().apply {
        arguments = Bundle().apply {
            putString("subjectId", SUBJECT.id)
        }
    }

    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment)
    }

    private val subjectName = MutableLiveData<String>()
    private val showAddItemDialog = MutableLiveData<Boolean>()

    private fun mockViewModel() {
        every { viewModel.subjectName } returns subjectName
        every { viewModel.showAddItemDialog } returns showAddItemDialog
    }

    private fun runOnUiThread(block: () -> Unit) {
        fragmentTestRule.activity.runOnUiThread(block)
    }

    @Test
    fun whenSubjectNameIsProvided_thenTitleMatchesSubjectName() {
        val title = SUBJECT.name
        subjectName.postValue(title)

        onView(withId(R.id.collapsingToolbar)).check(matches(withTitle(title)))
    }

    @Test
    fun whenFirstTabIsSelected_thenClassesFragmentIsDisplayed() {
        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(0))

        onView(withId(R.id.fragment_classes)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSecondTabIsSelected_thenTasksFragmentIsDisplayed() {
        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(1))

        onView(withId(R.id.fragment_tasks)).check(matches(isDisplayed()))
    }

    @Test
    fun whenAddButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btAdd)).perform(click())

        verify { viewModel.showAddItemDialog() }
    }

    @Test
    fun whenShowAddItemDialogIsTrue_thenAddItemDialogIsDisplayed() {
        runOnUiThread { showAddItemDialog.postValue(true) }

        onView(withId(R.id.dialog_add_subject_item)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun whenShowAddItemDialogIsFalse_thenAddItemDialogIsNotDisplayed() {
        runOnUiThread { showAddItemDialog.postValue(false) }

        onView(withId(R.id.dialog_add_subject_item)).check(matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun whenShowAddItemDialogSwitchedFromTrueToFalse_thenAddItemDialogSwitchesFromDisplayedToNotDisplayed() {
        runOnUiThread { showAddItemDialog.postValue(true) }
        onView(withId(R.id.dialog_add_subject_item)).check(matches(ViewMatchers.isDisplayed()))

        runOnUiThread { showAddItemDialog.postValue(false) }
        onView(withId(R.id.dialog_add_subject_item)).check(matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun whenParentViewGroupIsTouched_thenViewModelIsNotifiedToDismissAddItemDialog() {
        onView(withId(R.id.fragment_subject_detail)).perform(click())

        verify { viewModel.dismissAddItemDialog() }
    }

}