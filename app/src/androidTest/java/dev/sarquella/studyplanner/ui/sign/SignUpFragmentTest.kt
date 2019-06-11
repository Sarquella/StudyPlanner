package dev.sarquella.studyplanner.ui.sign

import android.view.inputmethod.EditorInfo
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.EMAIL
import dev.sarquella.studyplanner.PASSWORD
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.helpers.makeVerifyWaitForLiveData
import dev.sarquella.studyplanner.rules.DataBindingTestRule
import dev.sarquella.studyplanner.rules.FragmentTestRule
import dev.sarquella.studyplanner.ui.sign.abstractions.SignViewModel
import dev.sarquella.studyplanner.ui.sign.signUp.SignUpFragment
import dev.sarquella.studyplanner.ui.sign.signUp.SignUpViewModel
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
class SignUpFragmentTest {

    companion object {

        private val viewModel: SignUpViewModel = mockk(relaxUnitFun = true)

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
    val fragmentTestRule = FragmentTestRule<SignUpFragment>()

    @get:Rule
    val dataBindingTestRule = DataBindingTestRule(fragmentTestRule)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val navController: NavController = mockk(relaxUnitFun = true)
    private val fragment = SignUpFragment()

    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment, navController)
    }

    private val authState = MutableLiveData<SignViewModel.AuthState>()
    private val navToNext = MutableLiveData<Boolean>()
    private val isButtonEnabled = MutableLiveData<Boolean>()

    private fun mockViewModel() {
        every { viewModel.authState } returns authState
        every { viewModel.navToNext } returns navToNext
        every { viewModel.isButtonEnabled } returns isButtonEnabled
    }

    @Test
    fun checkStaticUIisCorrect() {
        onView(withId(R.id.tvTitle)).check(matches(withText(R.string.Sign_Up)))
        onView(withId(R.id.btSign)).check(matches(withText(R.string.Sign_Up)))
        onView(withId(R.id.tvNavToNext)).check(matches(withText(R.string.already_have_an_account)))
        onView(withId(R.id.btNavToNext)).check(matches(withText(R.string.Sign_In)))
        onView(withId(R.id.etPassword)).check(matches(withInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)))
    }

    @Test
    fun checkInitialUIisCorrect() {
        onView(withId(R.id.buttonsContainer)).check(matches(isDisplayed())) //Visible
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed()))) //Invisible
        onView(withId(R.id.errorCard)).check(matches(not(isDisplayed())))   //Invisible
    }

    @Test
    fun whenAuthStateIsAuthenticating_thenUIisCorrect() {

        authState.postValue(SignViewModel.AuthState(SignViewModel.AuthState.Status.AUTHENTICATING))

        onView(withId(R.id.buttonsContainer)).check(matches(not(isDisplayed()))) //Invisible
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))           //Visible
        onView(withId(R.id.errorCard)).check(matches(not(isDisplayed())))        //Invisible
    }

    @Test
    fun whenAuthStateIsInvalidAuthentication_thenUIisCorrect() {

        val errorMessage = "Error message"
        authState.postValue(
            SignViewModel.AuthState(SignViewModel.AuthState.Status.INVALID_AUTHENTICATION, errorMessage)
        )

        onView(withId(R.id.buttonsContainer)).check(matches(isDisplayed())) //Visible
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed()))) //Invisible
        onView(withId(R.id.errorCard)).check(matches(isDisplayed()))        //Visible
        onView(withId(R.id.tvError)).check(matches(withText(errorMessage)))
    }

    @Test
    fun whenAuthStateIsAuthenticated_thenNavToAppIsPerformed() {
        authState.postValue(SignViewModel.AuthState(SignViewModel.AuthState.Status.AUTHENTICATED))

        makeVerifyWaitForLiveData()

        verify { navController.navigate(R.id.action_nav_to_app_graph) }
    }

    @Test
    fun whenWritingInEmailEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etEmail)).perform(typeText(EMAIL))

        verify { viewModel.onEmailChanged(EMAIL) }
    }

    @Test
    fun whenClearingEmailEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etEmail)).perform(typeText(EMAIL))
        onView(withId(R.id.etEmail)).perform(clearText())

        verify { viewModel.onEmailChanged("") }
    }

    @Test
    fun whenWritingInPasswordEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD))

        verify { viewModel.onPasswordChanged(PASSWORD) }
    }

    @Test
    fun whenClearingPasswordEditText_thenViewModelIsNotified() {
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD))
        onView(withId(R.id.etPassword)).perform(clearText())

        verify { viewModel.onPasswordChanged("") }
    }

    @Test
    fun checkEditTextsAreEmptyInitially() {
        onView(withId(R.id.etEmail)).check(matches(withText("")))
        onView(withId(R.id.etPassword)).check(matches(withText("")))
    }

    @Test
    fun checkButtonIsDisabledInitially() {
        onView(withId(R.id.btSign)).check(matches(not(isEnabled())))
    }

    @Test
    fun whenIsButtonEnabledIsFalse_thenSignButtonIsDisabled() {
        isButtonEnabled.postValue(false)

        onView(withId(R.id.btSign)).check(matches(not(isEnabled())))
    }

    @Test
    fun whenIsButtonEnabledIsTrue_thenSignButtonIsEnabled() {
        isButtonEnabled.postValue(true)

        onView(withId(R.id.btSign)).check(matches(isEnabled()))
    }

    @Test
    fun whenSignButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.etEmail)).perform(typeText(EMAIL))
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD))
        isButtonEnabled.postValue(true)

        onView(withId(R.id.btSign)).perform(click())

        verify { viewModel.sign(EMAIL, PASSWORD) }
    }

    @Test
    fun whenNavToNextButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btNavToNext)).perform(click())

        verify { viewModel.navToNext() }
    }

    @Test
    fun whenNavToNextIsTrue_thenNavToSignInIsPerformed() {
        navToNext.postValue(true)

        makeVerifyWaitForLiveData()

        verify { navController.navigate(R.id.action_nav_to_sign_in) }
    }
}