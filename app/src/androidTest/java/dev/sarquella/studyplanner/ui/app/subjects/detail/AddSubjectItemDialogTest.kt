package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.helpers.setExpanded
import dev.sarquella.studyplanner.rules.FragmentTestRule
import dev.sarquella.studyplanner.ui.app.subjects.detail.classes.AddNewClassDialogViewModel
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import dev.sarquella.studyplanner.ui.app.subjects.detail.classes.ClassesViewModel
import dev.sarquella.studyplanner.ui.app.subjects.detail.tasks.AddNewTaskDialogViewModel
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
import dev.sarquella.studyplanner.ui.app.subjects.detail.tasks.TasksViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.not
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
class AddSubjectItemDialogTest {

    companion object {

        private val viewModel: AddSubjectItemViewModel = mockk(relaxUnitFun = true)


        private val subjectDetailViewModel: SubjectDetailViewModel = mockk(relaxed = true)
        private val addNewClassDialogViewModel: AddNewClassDialogViewModel = mockk(relaxed = true)
        private val addNewTaskDialogViewModel: AddNewTaskDialogViewModel = mockk(relaxed = true)
        private val classesViewModel: ClassesViewModel = mockk(relaxed = true)
        private val classListAdapter: ClassListAdapter = mockk(relaxed = true)
        private val tasksViewModel: TasksViewModel = mockk(relaxed = true)
        private val taskListAdapter: TaskListAdapter = mockk(relaxed = true)
        private val koinModule = module {
            viewModel { viewModel }
            viewModel { subjectDetailViewModel }
            viewModel { addNewClassDialogViewModel }
            viewModel { addNewTaskDialogViewModel }
            viewModel { classesViewModel }
            viewModel { tasksViewModel }
            factory { classListAdapter }
            factory { taskListAdapter }
        }

        @BeforeClass
        @JvmStatic
        fun beforeClass() {

            every { subjectDetailViewModel.subjectName.value } returns SUBJECT.name

            every { addNewClassDialogViewModel.classTypes } returns listOf("Class")
            every { addNewClassDialogViewModel.errorMessage.value } returns null
            every { addNewClassDialogViewModel.isAddButtonEnabled.value } returns false

            every { addNewTaskDialogViewModel.taskTypes } returns listOf("Task")
            every { addNewTaskDialogViewModel.errorMessage.value } returns null
            every { addNewTaskDialogViewModel.isAddButtonEnabled.value } returns false

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
        onView(withId(R.id.btAdd)).perform(setExpanded(true))
    }

    private val dismissDialog = MutableLiveData<Boolean>()
    private val showAddNewClassDialog = MutableLiveData<Boolean>()
    private val showAddNewTaskDialog = MutableLiveData<Boolean>()

    private fun mockViewModel() {
        every { viewModel.dismissDialog } returns dismissDialog
        every { viewModel.showAddNewClassDialog } returns showAddNewClassDialog
        every { viewModel.showAddNewTaskDialog } returns showAddNewTaskDialog
    }

    private fun runOnUiThread(block: () -> Unit) {
        fragmentTestRule.activity.runOnUiThread(block)
    }

    @Test
    fun whenNewClassButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btNewClass)).perform(click())

        verify { viewModel.addNewClass() }
    }

    @Test
    fun whenNewTaskButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btNewTask)).perform(click())

        verify { viewModel.addNewTask() }
    }

    @Test
    fun whenDismissDialogIsFalse_thenDialogIsDisplayed() {
        runOnUiThread { dismissDialog.postValue(false) }

        onView(withId(R.id.dialog_add_subject_item)).check(matches(isDisplayed()))
    }

    @Test
    fun whenDismissDialogIsTrue_thenDialogIsNotDisplayed() {
        runOnUiThread { dismissDialog.postValue(true) }

        onView(withId(R.id.dialog_add_subject_item)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenShowAddNewClassDialogIsFalse_thenAddNewClassDialogIsNotShown() {
        showAddNewClassDialog.postValue(false)

        onView(withId(R.id.dialog_add_new_class)).check(doesNotExist())
    }

    @Test
    fun whenShowAddNewClassDialogIsTrue_thenAddNewClassDialogIsShown() {
        showAddNewClassDialog.postValue(true)

        onView(withId(R.id.dialog_add_new_class)).check(matches(isDisplayed()))
    }

    @Test
    fun whenShowAddNewTaskDialogIsFalse_thenAddNewTaskDialogIsNotShown() {
        showAddNewTaskDialog.postValue(false)

        onView(withId(R.id.dialog_add_new_task)).check(doesNotExist())
    }

    @Test
    fun whenShowAddNewTaskDialogIsTrue_thenAddNewTaskDialogIsShown() {
        showAddNewTaskDialog.postValue(true)

        onView(withId(R.id.dialog_add_new_task)).check(matches(isDisplayed()))
    }
}