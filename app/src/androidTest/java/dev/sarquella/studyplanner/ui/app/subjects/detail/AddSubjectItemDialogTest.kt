package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.helpers.setExpanded
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
        fragmentTestRule.setFragment(fragment)
        onView(withId(R.id.btAdd)).perform(setExpanded(true))
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
}