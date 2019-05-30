package dev.sarquella.studyplanner.ui.app.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.USER
import dev.sarquella.studyplanner.helpers.makeVerifyWaitForLiveData
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
class ProfileFragmentTest {

    companion object {

        private val viewModel: ProfileViewModel = mockk(relaxUnitFun = true)

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
    val fragmentTestRule = FragmentTestRule<ProfileFragment>()

    @get:Rule
    val dataBindingTestRule = DataBindingTestRule(fragmentTestRule)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val navController: NavController = mockk(relaxUnitFun = true)
    private val fragment = ProfileFragment()

    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment, navController)
    }

    private val returnToSign = MutableLiveData<Boolean>()

    private fun mockViewModel() {
        every { viewModel.user } returns USER
        every { viewModel.returnToSign } returns returnToSign
    }

    @Test
    fun whenUserIsProvided_thenEmailMatchesUserEmail() {
        onView(withId(R.id.tvEmail)).check(matches(withText(USER.email)))
    }

    @Test
    fun whenSignOutButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btSignOut)).perform(click())

        verify { viewModel.signOut() }
    }

    @Test
    fun whenReturnToSignIsTrue_thenNavToSignIsPerformed() {
        returnToSign.postValue(true)

        makeVerifyWaitForLiveData()

        verify { navController.navigate(R.id.action_nav_to_sign_graph) }
    }


}