package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.sarquella.studyplanner.*
import dev.sarquella.studyplanner.helpers.enums.TaskType
import dev.sarquella.studyplanner.rules.DialogTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.*
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
class AddNewTaskDialogFragmentTest {

    companion object {

        private val viewModel: AddNewTaskDialogViewModel = mockk(relaxUnitFun = true)

        private val koinModule = module {
            viewModel { viewModel }
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
    val dialogTestRule = DialogTestRule<AddNewTaskDialogFragment>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dialog = AddNewTaskDialogFragment.newInstance(SUBJECT_ID)

    @Before
    fun beforeEach() {
        mockViewModel()
        dialogTestRule.setDialog(dialog)
    }

    private val isAddButtonEnabled = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String?>()
    private val dismiss = MutableLiveData<Boolean>()
    private val taskTypes = TaskType.toList(InstrumentationRegistry.getInstrumentation().targetContext)

    private fun mockViewModel() {
        every { viewModel.isAddButtonEnabled } returns isAddButtonEnabled
        every { viewModel.errorMessage } returns errorMessage
        every { viewModel.dismiss } returns dismiss
        every { viewModel.taskTypes } returns taskTypes
    }

    @Test
    fun whenIsAddButtonEnabledIsTrue_thenAddButtonIsEnabled() {
        isAddButtonEnabled.postValue(false)

        onView(withId(R.id.btAdd)).check(matches(not(isEnabled())))
    }

    @Test
    fun whenIsAddButtonEnabledIsFalse_thenAddButtonIsDisabled() {
        isAddButtonEnabled.postValue(true)

        onView(withId(R.id.btAdd)).check(matches(isEnabled()))
    }

    @Test
    fun checkInitialTaskTypeSpinnerValueIsPractice() {
        onView(withId(R.id.spTaskType)).check(matches(withSpinnerText(R.string.Practice)))
    }

    @Test
    fun checkFirstTaskTypeSpinnerValueIsPractice() {
        onView(withId(R.id.spTaskType)).perform(click())
        onData(`is`(instanceOf(String::class.java))).inRoot(isPlatformPopup()).atPosition(0).perform(click())
        onView(withId(R.id.spTaskType)).check(matches(withSpinnerText(R.string.Practice)))
    }

    @Test
    fun checkSecondTaskTypeSpinnerValueIsSeminar() {
        onView(withId(R.id.spTaskType)).perform(click())
        onData(`is`(instanceOf(String::class.java))).inRoot(isPlatformPopup()).atPosition(1).perform(click())
        onView(withId(R.id.spTaskType)).check(matches(withSpinnerText(R.string.Seminar)))
    }

    @Test
    fun checkThirdTaskTypeSpinnerValueIsExam() {
        onView(withId(R.id.spTaskType)).perform(click())
        onData(`is`(instanceOf(String::class.java))).inRoot(isPlatformPopup()).atPosition(2).perform(click())
        onView(withId(R.id.spTaskType)).check(matches(withSpinnerText(R.string.Exam)))
    }

    @Test
    fun whenWritingInNameEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etName)).perform(typeText(TASK_NAME))

        verify { viewModel.onNameChanged(TASK_NAME) }
    }

    @Test
    fun whenClearingNameEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etName)).perform(typeText(TASK_NAME))
        onView(withId(R.id.etName)).perform(clearText())

        verify { viewModel.onNameChanged("") }
    }

    @Test
    fun whenWritingInDayEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etDay)).perform(typeText(DAY))

        verify { viewModel.onDayChanged(DAY) }
    }

    @Test
    fun whenClearingDayEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etDay)).perform(typeText(DAY))
        onView(withId(R.id.etDay)).perform(clearText())

        verify { viewModel.onDayChanged("") }
    }

    @Test
    fun whenWritingInTimeEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etTime)).perform(typeText(END_TIME))

        verify { viewModel.onTimeChanged(END_TIME) }
    }

    @Test
    fun whenClearingTimeEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etTime)).perform(typeText(END_TIME))
        onView(withId(R.id.etTime)).perform(clearText())

        verify { viewModel.onTimeChanged("") }
    }

    @Test
    fun whenErrorMessageIsNull_thenErrorTextViewIsNotDisplayed() {
        errorMessage.postValue(null)

        onView(withId(R.id.tvError)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenErrorMessageIsNotNull_thenErrorTextViewIsDisplayedWithErrorMessage() {
        val message = "Error message"
        errorMessage.postValue(message)

        onView(withId(R.id.tvError)).check(matches(isDisplayed()))
        onView(withId(R.id.tvError)).check(matches(withText(message)))
    }

    @Test
    fun whenCancelButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btCancel)).perform(click())

        verify { viewModel.cancel() }
    }

    @Test
    fun whenAddButtonIsClicked_andFieldsAreFilled_thenViewModelIsNotified() {
        isAddButtonEnabled.postValue(true)
        onView(withId(R.id.etName)).perform(typeText(TASK_NAME))
        onView(withId(R.id.etDay)).perform(typeText(DAY))
        onView(withId(R.id.etTime)).perform(typeText(END_TIME))
        onView(withId(R.id.etTime)).perform(closeSoftKeyboard())

        onView(withId(R.id.btAdd)).perform(click())

        verify { viewModel.add(TASK_NAME, taskTypes[0], DAY, END_TIME) }
    }

    @Test
    fun whenDismissIsFalse_thenDialogDoesNotDismiss() {
        dismiss.postValue(false)

        onView(withId(R.id.dialog_add_new_task)).check(matches(isDisplayed()))
    }

    @Test
    fun whenDismissIsTrue_thenDialogDismisses() {
        dismiss.postValue(true)

        onView(withId(R.id.dialog_add_new_task)).check(doesNotExist())
    }
}