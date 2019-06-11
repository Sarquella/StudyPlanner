package dev.sarquella.studyplanner.ui.app.calendar.classes

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sarquella.studyplanner.data.entities.Class
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.CLASS
import dev.sarquella.studyplanner.CLASS_2
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.helpers.*
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import dev.sarquella.studyplanner.rules.FragmentTestRule
import dev.sarquella.studyplanner.ui.app.calendar.CalendarViewModel
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.allOf
import org.junit.*
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@RunWith(AndroidJUnit4::class)
class DailyClassesFragmentTest {

    companion object {

        private val viewModel: CalendarViewModel = mockk(relaxUnitFun = true)

        private val koinModule = module {
            viewModel { viewModel }
            factory { (options: FirestoreRecyclerOptions<Class>) ->
                ClassListAdapter(
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
    val fragmentTestRule = FragmentTestRule<DailyClassesFragment>()

    private val fragment = DailyClassesFragment()


    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment)
    }

    private val classesList = MutableLiveData<ListBuilder<Class>>()
    private val listBuilder: ListBuilder<Class> = mockk()
    private val recyclerOptions = RecyclerOptions(fragment, Class::class.java)

    private fun mockViewModel() {
        every { viewModel.classesList } returns classesList
    }

    @Test
    fun whenListWithSingleItemIsProvided_thenShowsCorrespondingItem() {
        every { listBuilder.build(any()) } returns recyclerOptions.withItems(mutableListOf(CLASS))
        classesList.postValue(listBuilder)

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
             .check(matches(withText(CLASS.type.value)))

         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvSubject))
             .check(matches(withText(CLASS.subjectName)))

         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
             .check(matches(hasBackgroundColor(CLASS.subjectColor)))

         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvDay))
             .check(matches(withText(DateUtils.serializeDay(CLASS.startDate))))

         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvStartTime))
             .check(matches(withText(DateUtils.serializeTime(CLASS.startDate))))

         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvEndTime))
             .check(matches(withText(DateUtils.serializeTime(CLASS.endDate))))
    }

    @Test
    fun whenListWithMultipleItemsIsProvided_thenShowsCorrespondingItems() {
        every { listBuilder.build(any()) } returns
                recyclerOptions.withItems(mutableListOf(CLASS, CLASS_2))
        classesList.postValue(listBuilder)

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
             .check(matches(withText(CLASS.type.value)))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvSubject))
             .check(matches(withText(CLASS.subjectName)))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.colorIndicator))
             .check(matches(hasBackgroundColor(CLASS.subjectColor)))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvDay))
             .check(matches(withText(DateUtils.serializeDay(CLASS.startDate))))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvStartTime))
             .check(matches(withText(DateUtils.serializeTime(CLASS.startDate))))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvEndTime))
             .check(matches(withText(DateUtils.serializeTime(CLASS.endDate))))

         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvType))
             .check(matches(withText(CLASS_2.type.value)))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvSubject))
             .check(matches(withText(CLASS_2.subjectName)))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.colorIndicator))
             .check(matches(hasBackgroundColor(CLASS_2.subjectColor)))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvDay))
             .check(matches(withText(DateUtils.serializeDay(CLASS_2.startDate))))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvStartTime))
             .check(matches(withText(DateUtils.serializeTime(CLASS_2.startDate))))
         onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvEndTime))
             .check(matches(withText(DateUtils.serializeTime(CLASS_2.endDate))))
    }

}