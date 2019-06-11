package dev.sarquella.studyplanner.ui.app.subjects

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.SUBJECT_2
import dev.sarquella.studyplanner.data.entities.Subject
import dev.sarquella.studyplanner.helpers.RecyclerOptions
import dev.sarquella.studyplanner.helpers.hasBackgroundColor
import dev.sarquella.studyplanner.helpers.withRecyclerView
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
class SubjectsFragmentTest {

    companion object {

        private val viewModel: SubjectsViewModel = mockk(relaxUnitFun = true)

        private val addSubjectsViewModel: AddSubjectViewModel = mockk(relaxed = true)
        private val koinModule = module {
            viewModel { viewModel }
            viewModel { addSubjectsViewModel }
            factory { (options: FirestoreRecyclerOptions<Subject>) -> SubjectListAdapter(options) }
        }

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            every { addSubjectsViewModel.isAddButtonEnabled.value } returns false

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

    private val navController: NavController = mockk(relaxUnitFun = true)
    private val fragment = SubjectsFragment()


    @Before
    fun beforeEach() {
        mockViewModel()
    }

    private val showAddSubjectDialog = MutableLiveData<Boolean>()
    private val recyclerOptions = RecyclerOptions(fragment, Subject::class.java)

    private fun mockViewModel() {
        every { viewModel.showAddSubjectDialog } returns showAddSubjectDialog
    }

    private fun runOnUiThread(block: () -> Unit) {
        fragmentTestRule.activity.runOnUiThread(block)
    }

    @Test
    fun whenAddButtonIsClicked_thenViewModelIsNotified() {
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withNoItems()
        fragmentTestRule.setFragment(fragment, navController)
        onView(withId(R.id.btAdd)).perform(click())

        verify { viewModel.showAddSubjectDialog() }
    }

    @Test
    fun whenShowAddSubjectDialogIsTrue_thenAddSubjectDialogIsDisplayed() {
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withNoItems()
        fragmentTestRule.setFragment(fragment, navController)

        runOnUiThread { showAddSubjectDialog.postValue(true) }

        onView(withId(R.id.dialog_add_subject)).check(matches(isDisplayed()))
    }

    @Test
    fun whenShowAddSubjectDialogIsFalse_thenAddSubjectDialogIsNotDisplayed() {
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withNoItems()
        fragmentTestRule.setFragment(fragment, navController)

        runOnUiThread { showAddSubjectDialog.postValue(false) }

        onView(withId(R.id.dialog_add_subject)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenListWithSingleItemIsProvided_thenShowsCorrespondingItem() {
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withItems(mutableListOf(SUBJECT))
        fragmentTestRule.setFragment(fragment, navController)

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(SUBJECT.name)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(SUBJECT.color)))
    }

    @Test
    fun whenListWithMultipleItemsIsProvided_thenShowsCorrespondingItems() {
        every { viewModel.subjectsList.build(any()) } returns
                recyclerOptions.withItems(mutableListOf(SUBJECT, SUBJECT_2))
        fragmentTestRule.setFragment(fragment, navController)

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(SUBJECT.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(SUBJECT.color)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvName))
            .check(matches(withText(SUBJECT_2.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(SUBJECT_2.color)))
    }

    @Test
    fun whenListItemSelected_thenNavigatesToDetail() {
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withItems(mutableListOf(SUBJECT))
        fragmentTestRule.setFragment(fragment, navController)

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<SubjectViewHolder>(0, click()))

        verify { navController.navigate(SubjectsFragmentDirections.actionNavToSubjectDetail(SUBJECT.id)) }
    }
}