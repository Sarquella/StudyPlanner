package dev.sarquella.studyplanner.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.helpers.makeVerifyWaitForLiveData
import dev.sarquella.studyplanner.rules.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
class LaunchFragmentTest {

    companion object {

        private val viewModel: LaunchViewModel = mockk(relaxUnitFun = true)

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
    val fragmentTestRule = FragmentTestRule<LaunchFragment>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val navController: NavController = mockk(relaxUnitFun = true)
    private val fragment = LaunchFragment()

    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment, navController)
    }

    private val destination = MutableLiveData<LaunchViewModel.Destination>()

    private fun mockViewModel() {
        every { viewModel.destination } returns destination
    }

    @Test
    fun whenDestinationIsSign_thenNavToSignGraph() {
        destination.postValue(LaunchViewModel.Destination.SIGN)

        makeVerifyWaitForLiveData()

        verify { navController.navigate(R.id.action_nav_to_sign_graph) }
    }

    @Test
    fun whenDestinationIsMain_thenNavToAppGraph() {
        destination.postValue(LaunchViewModel.Destination.APP)

        makeVerifyWaitForLiveData()

        verify { navController.navigate(R.id.action_nav_to_app_graph) }
    }
}