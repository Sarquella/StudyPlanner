package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.helpers.withTitle
import dev.sarquella.studyplanner.rules.DataBindingTestRule
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
class SubjectDetailFragmentTest {

    companion object {

        private val viewModel: SubjectDetailViewModel = mockk(relaxUnitFun = true)

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            loadKoinModules(
                module {
                    viewModel { viewModel }
                }
            )
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
        fragment.arguments = Bundle()
        fragmentTestRule.setFragment(fragment)
    }

    private val subjectName = MutableLiveData<String>()

    private fun mockViewModel() {
        every { viewModel.subjectName } returns subjectName
    }

    @Test
    fun whenSubjectNameIsProvided_thenTitleMatchesSubjectName() {
        val title = SUBJECT.name
        subjectName.postValue(title)

        onView(withId(R.id.collapsingToolbar)).check(matches(withTitle(title)))
    }

}