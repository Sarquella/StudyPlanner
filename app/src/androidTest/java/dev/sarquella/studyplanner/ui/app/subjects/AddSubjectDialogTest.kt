package dev.sarquella.studyplanner.ui.app.subjects

import androidx.annotation.ColorRes
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT_NAME
import dev.sarquella.studyplanner.helpers.checkButton
import dev.sarquella.studyplanner.helpers.extensions.toHexColor
import dev.sarquella.studyplanner.helpers.setExpanded
import dev.sarquella.studyplanner.rules.DataBindingTestRule
import dev.sarquella.studyplanner.rules.FragmentTestRule
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
class AddSubjectDialogTest {

    companion object {

        private val viewModel: AddSubjectViewModel = mockk(relaxUnitFun = true)

        private val subjectsViewModel: SubjectsViewModel = mockk(relaxed = true)
        private val subjectListAdapter: SubjectListAdapter = mockk(relaxed = true)
        private val koinModule = module {
            viewModel { viewModel }
            viewModel { subjectsViewModel }
            factory { subjectListAdapter }
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
    val fragmentTestRule = FragmentTestRule<SubjectsFragment>()

    @get:Rule
    val dataBindingTestRule = DataBindingTestRule(fragmentTestRule)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fragment = SubjectsFragment()

    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment)
        onView(withId(R.id.btAdd)).perform(setExpanded(true))
    }

    private val isAddButtonEnabled = MutableLiveData<Boolean>()
    private val dismiss = MutableLiveData<Boolean>()

    private fun mockViewModel() {
        every { viewModel.isAddButtonEnabled } returns isAddButtonEnabled
        every { viewModel.dismiss } returns dismiss
    }

    private fun runOnUiThread(block: () -> Unit) {
        fragmentTestRule.activity.runOnUiThread(block)
    }

    private fun getColorHex(@ColorRes resId: Int): String =
        ContextCompat.getColor(fragmentTestRule.activity.applicationContext, resId).toHexColor()

    @Test
    fun whenIsAddButtonEnabledIsTrue_thenAddButtonIsEnabled() {
        isAddButtonEnabled.postValue(true)

        onView(withId(R.id.btAddSubject)).check(matches(isEnabled()))
    }

    @Test
    fun whenIsAddButtonEnabledIsFalse_thenAddButtonIsDisabled() {
        isAddButtonEnabled.postValue(false)

        onView(withId(R.id.btAddSubject)).check(matches(not(isEnabled())))
    }

    @Test
    fun whenDismissIsTrue_thenDialogIsNotDisplayed() {
        runOnUiThread { dismiss.postValue(true) }
        onView(withId(R.id.dialog_add_subject)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenDismissIsFalse_thenDialogIsDisplayed() {
        runOnUiThread { dismiss.postValue(false) }
        onView(withId(R.id.dialog_add_subject)).check(matches(isDisplayed()))
    }

    @Test
    fun whenWritingInNameEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etName)).perform(typeText(SUBJECT_NAME))

        verify { viewModel.onNameChanged(SUBJECT_NAME) }
    }

    @Test
    fun whenClearingNameEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etName)).perform(typeText(SUBJECT_NAME))
        onView(withId(R.id.etName)).perform(clearText())

        verify { viewModel.onNameChanged("") }
    }

    @Test
    fun whenCancelButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btCancel)).perform(click())

        verify { viewModel.cancel() }
    }

    @Test
    fun whenAddButtonIsClicked_andBlueColorSelected_thenViewModelIsNotified() {
        isAddButtonEnabled.postValue(true)
        onView(withId(R.id.etName)).perform(typeText(SUBJECT_NAME))
        onView(withId(R.id.etName)).perform(closeSoftKeyboard())
        onView(withId(R.id.rgColorSelector)).perform(checkButton(R.id.rbBlue))

        onView(withId(R.id.btAddSubject)).perform(click())

        verify { viewModel.add(SUBJECT_NAME, getColorHex(R.color.blue)) }
    }

    @Test
    fun whenAddButtonIsClicked_andRedColorSelected_thenViewModelIsNotified() {
        isAddButtonEnabled.postValue(true)
        onView(withId(R.id.etName)).perform(typeText(SUBJECT_NAME))
        onView(withId(R.id.etName)).perform(closeSoftKeyboard())
        onView(withId(R.id.rgColorSelector)).perform(checkButton(R.id.rbRed))

        onView(withId(R.id.btAddSubject)).perform(click())

        verify { viewModel.add(SUBJECT_NAME, getColorHex(R.color.red)) }
    }

}