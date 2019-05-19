package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.helpers.setExpanded
import dev.sarquella.studyplanner.rules.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.not
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
class AddSubjectItemDialogTest {

    companion object {

        private val viewModel: AddSubjectItemViewModel = mockk(relaxUnitFun = true)

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            val subjectDetailViewModel: SubjectDetailViewModel = mockk(relaxed = true)
            every { subjectDetailViewModel.subjectName.value } returns SUBJECT.name
            loadKoinModules(
                module {
                    viewModel { viewModel }
                    viewModel { subjectDetailViewModel }
                }
            )
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

    private fun mockViewModel() {
        every { viewModel.dismissDialog } returns dismissDialog
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
}