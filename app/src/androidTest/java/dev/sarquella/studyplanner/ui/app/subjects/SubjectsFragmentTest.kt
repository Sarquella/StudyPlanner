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
class SubjectsFragmentTest {

    companion object {

        private val viewModel: SubjectsViewModel = mockk(relaxUnitFun = true)

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            val addSubjectsViewModel: AddSubjectViewModel = mockk(relaxed = true)
            every { addSubjectsViewModel.isAddButtonEnabled.value } returns false

            loadKoinModules(
                module {
                    viewModel { viewModel }
                    viewModel { addSubjectsViewModel }
                    factory { (options: FirestoreRecyclerOptions<Subject>) -> SubjectListAdapter(options) }
                }
            )
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
        fragmentTestRule.setFragment(fragment, navController)
    }

    private val showAddSubjectDialog = MutableLiveData<Boolean>()
    private val recyclerOptions = RecyclerOptions(fragment, Subject::class.java)

    private fun mockViewModel() {
        every { viewModel.showAddSubjectDialog } returns showAddSubjectDialog
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withNoItems()
    }

    private fun runOnUiThread(block: () -> Unit) {
        fragmentTestRule.activity.runOnUiThread(block)
    }

   /* @Test
    fun whenAddButtonIsClicked_thenViewModelIsNotified() {
        onView(withId(R.id.btAdd)).perform(click())

        verify { viewModel.showAddSubjectDialog() }
    }

    @Test
    fun whenShowAddSubjectDialogIsTrue_thenAddSubjectDialogIsDisplayed() {
        runOnUiThread { showAddSubjectDialog.postValue(true) }

        onView(withId(R.id.dialog_add_subject)).check(matches(isDisplayed()))
    }

    @Test
    fun whenShowAddSubjectDialogIsFalse_thenAddSubjectDialogIsNotDisplayed() {
        runOnUiThread { showAddSubjectDialog.postValue(false) }

        onView(withId(R.id.dialog_add_subject)).check(matches(not(isDisplayed())))
    }*/

    @Test
    fun whenListWithSingleItemIsProvided_thenShowsCorrespondingItem() {
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withItems(mutableListOf(SUBJECT))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(SUBJECT.name)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(SUBJECT.color)))
    }

    @Test
    fun whenListWithMultipleItemsIsProvided_thenShowsCorrespondingItems() {
        val subject1 = Subject("Subject1", "#FFFFFF")
        val subject2 = Subject("Subject2", "#000000")
        every { viewModel.subjectsList.build(any()) } returns
                recyclerOptions.withItems(mutableListOf(subject1, subject2))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(subject1.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(subject1.color)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvName))
            .check(matches(withText(subject2.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(subject2.color)))
    }

    /*@Test
    fun whenListItemSelected_thenNavigatesToDetail() {
        every { viewModel.subjectsList.build(any()) } returns recyclerOptions.withItems(mutableListOf(SUBJECT))

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<SubjectViewHolder>(0, click()))

        verify { navController.navigate(SubjectsFragmentDirections.actionNavToSubjectDetail(SUBJECT.id)) }
    }*/
}