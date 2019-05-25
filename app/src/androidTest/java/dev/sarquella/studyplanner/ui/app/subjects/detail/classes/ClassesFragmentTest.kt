package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.CLASS
import dev.sarquella.studyplanner.CLASS_2
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.SUBJECT_ID
import dev.sarquella.studyplanner.data.entities.Class
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
class ClassesFragmentTest {

    companion object {

        private val viewModel: ClassesViewModel = mockk(relaxUnitFun = true)

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            loadKoinModules(
                module {
                    viewModel { viewModel }
                    factory { (options: FirestoreRecyclerOptions<Class>) -> ClassListAdapter(options) }
                }
            )
        }
    }

    @get:Rule
    val fragmentTestRule = FragmentTestRule<ClassesFragment>()

    private val fragment = ClassesFragment.newInstance(SUBJECT_ID)


    @Before
    fun beforeEach() {
        mockViewModel()
        fragmentTestRule.setFragment(fragment)
    }

    private val recyclerOptions = RecyclerOptions(fragment, Class::class.java)

    private fun mockViewModel() {
        every { viewModel.classesList.build(any()) } returns recyclerOptions.withNoItems()
    }

    @Test
    fun whenListWithSingleItemIsProvided_thenShowsCorrespondingItem() {
        every { viewModel.classesList.build(any()) } returns recyclerOptions.withItems(mutableListOf(CLASS))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
            .check(matches(withText(CLASS.type.value)))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvStartTime))
            .check(matches(withText(DateUtils.serialize(CLASS.startDate))))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvEndTime))
            .check(matches(withText(DateUtils.serialize(CLASS.endDate))))
    }

    @Test
    fun whenListWithMultipleItemsIsProvided_thenShowsCorrespondingItems() {
        every { viewModel.classesList.build(any()) } returns
                recyclerOptions.withItems(mutableListOf(CLASS, CLASS_2))


        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvType))
            .check(matches(withText(CLASS.type.value)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvStartTime))
            .check(matches(withText(DateUtils.serialize(CLASS.startDate))))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.tvEndTime))
            .check(matches(withText(DateUtils.serialize(CLASS.endDate))))

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvType))
            .check(matches(withText(CLASS_2.type.value)))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvStartTime))
            .check(matches(withText(DateUtils.serialize(CLASS_2.startDate))))
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.tvEndTime))
            .check(matches(withText(DateUtils.serialize(CLASS_2.endDate))))
    }

}