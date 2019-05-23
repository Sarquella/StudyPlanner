package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

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
import dev.sarquella.studyplanner.helpers.enums.ClassType
import dev.sarquella.studyplanner.rules.DialogTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.*
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
class AddNewClassDialogFragmentTest {

    companion object {

        private val viewModel: AddNewClassDialogViewModel = mockk(relaxUnitFun = true)

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
    val dialogTestRule = DialogTestRule<AddNewClassDialogFragment>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dialog = AddNewClassDialogFragment.newInstance(SUBJECT_ID)

    @Before
    fun beforeEach() {
        mockViewModel()
        dialogTestRule.setDialog(dialog)
    }

    private val isAddButtonEnabled = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String?>()
    private val dismiss = MutableLiveData<Boolean>()
    private val classTypes = ClassType.toList(InstrumentationRegistry.getInstrumentation().targetContext)

    private fun mockViewModel() {
        every { viewModel.isAddButtonEnabled } returns isAddButtonEnabled
        every { viewModel.errorMessage } returns errorMessage
        every { viewModel.dismiss } returns dismiss
        every { viewModel.classTypes } returns classTypes
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
    fun checkInitialClassTypeSpinnerValueIsTheory() {
        onView(withId(R.id.spClassType)).check(matches(withSpinnerText(R.string.Theory)))
    }

    @Test
    fun checkFirstClassTypeSpinnerValueIsTheory() {
        onView(withId(R.id.spClassType)).perform(click())
        onData(`is`(instanceOf(String::class.java))).inRoot(isPlatformPopup()).atPosition(0).perform(click())
        onView(withId(R.id.spClassType)).check(matches(withSpinnerText(R.string.Theory)))
    }

    @Test
    fun checkSecondClassTypeSpinnerValueIsPractice() {
        onView(withId(R.id.spClassType)).perform(click())
        onData(`is`(instanceOf(String::class.java))).inRoot(isPlatformPopup()).atPosition(1).perform(click())
        onView(withId(R.id.spClassType)).check(matches(withSpinnerText(R.string.Practice)))
    }

    @Test
    fun checkThirdClassTypeSpinnerValueIsSeminar() {
        onView(withId(R.id.spClassType)).perform(click())
        onData(`is`(instanceOf(String::class.java))).inRoot(isPlatformPopup()).atPosition(2).perform(click())
        onView(withId(R.id.spClassType)).check(matches(withSpinnerText(R.string.Seminar)))
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
    fun whenWritingInStartTimeEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etStartTime)).perform(typeText(START_TIME))

        verify { viewModel.onStartTimeChanged(START_TIME) }
    }

    @Test
    fun whenClearingStartTimeEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etStartTime)).perform(typeText(START_TIME))
        onView(withId(R.id.etStartTime)).perform(clearText())

        verify { viewModel.onStartTimeChanged("") }
    }

    @Test
    fun whenWritingInEndTimeEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etEndTime)).perform(typeText(END_TIME))

        verify { viewModel.onEndTimeChanged(END_TIME) }
    }

    @Test
    fun whenClearingEndTimeEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etEndTime)).perform(typeText(END_TIME))
        onView(withId(R.id.etEndTime)).perform(clearText())

        verify { viewModel.onEndTimeChanged("") }
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
        onView(withId(R.id.etDay)).perform(typeText(DAY))
        onView(withId(R.id.etStartTime)).perform(typeText(START_TIME))
        onView(withId(R.id.etEndTime)).perform(typeText(END_TIME))
        onView(withId(R.id.etEndTime)).perform(closeSoftKeyboard())

        onView(withId(R.id.btAdd)).perform(click())

        verify { viewModel.add(classTypes[0], DAY, START_TIME, END_TIME) }
    }

    @Test
    fun whenDismissIsFalse_thenDialogDoesNotDismiss() {
        dismiss.postValue(false)

        onView(withId(R.id.dialog_add_new_class)).check(matches(isDisplayed()))
    }

    @Test
    fun whenDismissIsTrue_thenDialogDismisses() {
        dismiss.postValue(true)

        onView(withId(R.id.dialog_add_new_class)).check(doesNotExist())
    }
}