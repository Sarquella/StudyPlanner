package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT_ID
import dev.sarquella.studyplanner.TASK
import dev.sarquella.studyplanner.TASK_2
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.helpers.RecyclerOptions
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import dev.sarquella.studyplanner.helpers.withRecyclerView
import dev.sarquella.studyplanner.rules.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
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
class TasksFragmentTest {

    companion object {

        private val viewModel: TasksViewModel = mockk(relaxUnitFun = true)

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            loadKoinModules(
                module {
                    viewModel { viewModel }
                    factory { (options: FirestoreRecyclerOptions<Task>) -> TaskListAdapter(options) }
                }
            )
        }
    }

    @get:Rule
    val fragmentTestRule = FragmentTestRule<TasksFragment>()

    private val fragment = TasksFragment.newInstance(SUBJECT_ID)


    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment)
    }

    private val recyclerOptions = RecyclerOptions(fragment, Task::class.java)

    private fun mockViewModel() {
        every { viewModel.tasksList.build(any()) } returns recyclerOptions.withNoItems()
    }

    @Test
    fun whenListWithSingleItemIsProvided_thenShowsCorrespondingItem() {
        every { viewModel.tasksList.build(any()) } returns recyclerOptions.withItems(mutableListOf(TASK))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
            .check(matches(withText(TASK.type.value)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(TASK.name)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvDeliveryTime))
            .check(matches(withText(DateUtils.serialize(TASK.deliveryDate))))
    }

    @Test
    fun whenListWithMultipleItemsIsProvided_thenShowsCorrespondingItems() {

        every { viewModel.tasksList.build(any()) } returns
                recyclerOptions.withItems(mutableListOf(TASK, TASK_2))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
            .check(matches(withText(TASK.type.value)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(TASK.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvDeliveryTime))
            .check(matches(withText(DateUtils.serialize(TASK.deliveryDate))))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvType))
            .check(matches(withText(TASK_2.type.value)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvName))
            .check(matches(withText(TASK_2.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvDeliveryTime))
            .check(matches(withText(DateUtils.serialize(TASK_2.deliveryDate))))
    }

}