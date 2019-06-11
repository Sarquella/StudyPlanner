package dev.sarquella.studyplanner.ui.app.calendar.tasks

import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.TASK
import dev.sarquella.studyplanner.TASK_2
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.helpers.*
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import dev.sarquella.studyplanner.rules.FragmentTestRule
import dev.sarquella.studyplanner.ui.app.calendar.CalendarViewModel
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
import io.mockk.every
import io.mockk.mockk
import org.junit.*
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@RunWith(AndroidJUnit4::class)
class DailyTasksFragmentTest {

    companion object {

        private val viewModel: CalendarViewModel = mockk(relaxUnitFun = true)

        private val koinModule = module {
            viewModel { viewModel }
            factory { (options: FirestoreRecyclerOptions<Task>) ->
                TaskListAdapter(
                    options
                )
            }
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
    val fragmentTestRule = FragmentTestRule<DailyTasksFragment>()

    private val fragment = DailyTasksFragment()


    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment)
    }

    private val tasksList = MutableLiveData<ListBuilder<Task>>()
    private val listBuilder: ListBuilder<Task> = mockk()
    private val recyclerOptions = RecyclerOptions(fragment, Task::class.java)

    private fun mockViewModel() {
        every { viewModel.tasksList } returns tasksList
    }

    @Test
    fun whenListWithSingleItemIsProvided_thenShowsCorrespondingItem() {
        every { listBuilder.build(any()) } returns recyclerOptions.withItems(mutableListOf(TASK))
        tasksList.postValue(listBuilder)

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
            .check(matches(withText(TASK.type.value)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvSubject))
            .check(matches(withText(TASK.subjectName)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(TASK.subjectColor)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(TASK.name)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvDate))
            .check(matches(withText(DateUtils.serializeDate(TASK.deliveryDate))))
    }

    @Test
    fun whenListWithMultipleItemsIsProvided_thenShowsCorrespondingItems() {
        every { listBuilder.build(any()) } returns
                recyclerOptions.withItems(mutableListOf(TASK, TASK_2))
        tasksList.postValue(listBuilder)


        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
            .check(matches(withText(TASK.type.value)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvSubject))
            .check(matches(withText(TASK.subjectName)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(TASK.subjectColor)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvName))
            .check(matches(withText(TASK.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvDate))
            .check(matches(withText(DateUtils.serializeDate(TASK.deliveryDate))))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvType))
            .check(matches(withText(TASK_2.type.value)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvSubject))
            .check(matches(withText(TASK_2.subjectName)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.colorIndicator))
            .check(matches(hasBackgroundColor(TASK_2.subjectColor)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvName))
            .check(matches(withText(TASK_2.name)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvDate))
            .check(matches(withText(DateUtils.serializeDate(TASK_2.deliveryDate))))
    }

}