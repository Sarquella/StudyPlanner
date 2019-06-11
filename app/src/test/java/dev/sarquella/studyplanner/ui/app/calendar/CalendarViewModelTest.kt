package dev.sarquella.studyplanner.ui.app.calendar

import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.Event
import dev.sarquella.studyplanner.data.vo.EventGroup
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.helpers.extensions.addObserver
import dev.sarquella.studyplanner.helpers.extensions.plusDays
import dev.sarquella.studyplanner.helpers.extensions.toGroupList
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.ClassRepo
import dev.sarquella.studyplanner.repo.TaskRepo
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class CalendarViewModelTest {

    private val classRepo: ClassRepo = mockk(relaxed = true)
    private val taskRepo: TaskRepo = mockk(relaxed = true)
    private val viewModel: CalendarViewModel

    private val classesEvents = MutableLiveData<Resource<List<Event>>>()
    private val tasksEvents = MutableLiveData<Resource<List<Event>>>()

    private val classList: ListBuilder<Class> = mockk()
    private val taskList: ListBuilder<Task> = mockk()

    init {
        every { classRepo.getClassesEvents() } returns classesEvents
        every { taskRepo.getTasksEvents() } returns tasksEvents

        every { classRepo.getClassesByDate(any()) } returns classList
        every { taskRepo.getTasksByDate(any()) } returns taskList

        viewModel = CalendarViewModel(classRepo, taskRepo)
    }

    private val classesEventsList: List<Event> = mockk(relaxed = true)
    private val classesEventsGroupList: List<EventGroup> = mockk(relaxed = true)

    private val tasksEventsList: List<Event> = mockk(relaxed = true)
    private val tasksEventsGroupList: List<EventGroup> = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        viewModel.groupedEvents.addObserver()

        mockkStatic("dev.sarquella.studyplanner.helpers.extensions.ListEventsKt")

        every { classesEventsList.toGroupList() } returns classesEventsGroupList
        every { tasksEventsList.toGroupList() } returns tasksEventsGroupList
        classesEvents.postValue(Resource(classesEventsList, mockk()))
        tasksEvents.postValue(Resource(tasksEventsList, mockk()))

    }

    @Nested
    inner class OnTabSelected {

        @Test
        fun `when first tab is selected then groupedEvents corresponds to classesEvents`() {
            viewModel.onTabSelected(0)

            assertThat(viewModel.groupedEvents.value).isEqualTo(classesEventsGroupList)
        }

        @Test
        fun `when second tab is selected then groupedEvents corresponds to tasksEvents`() {
            viewModel.onTabSelected(1)

            assertThat(viewModel.groupedEvents.value).isEqualTo(tasksEventsGroupList)
        }

    }

    @Nested
    inner class GroupedEvents {

        @Test
        fun `check initial groupedEvents corresponds to classEvents`() {
            val newClassesEventsGroupList: List<EventGroup> = mockk(relaxed = true)
            val newClassesEventsList: List<Event> = mockk(relaxed = true)
            every { newClassesEventsList.toGroupList() } returns newClassesEventsGroupList

            classesEvents.postValue(Resource(newClassesEventsList, mockk()))

            assertThat(viewModel.groupedEvents.value).isEqualTo(newClassesEventsGroupList)
        }

        @Test
        fun `when classesEvents is updated and first tab is selected then groupedEvents equals to classesEvents`() {
            viewModel.onTabSelected(0)

            val newClassesEventsGroupList: List<EventGroup> = mockk(relaxed = true)
            val newClassesEventsList: List<Event> = mockk(relaxed = true)
            every { newClassesEventsList.toGroupList() } returns newClassesEventsGroupList

            classesEvents.postValue(Resource(newClassesEventsList, mockk()))

            assertThat(viewModel.groupedEvents.value).isEqualTo(newClassesEventsGroupList)
        }

        @Test
        fun `when classesEvents is updated but second tab is selected then groupedEvents equals to tasksEvents`() {
            viewModel.onTabSelected(1)

            val newClassesEventsGroupList: List<EventGroup> = mockk(relaxed = true)
            val newClassesEventsList: List<Event> = mockk(relaxed = true)
            every { newClassesEventsList.toGroupList() } returns newClassesEventsGroupList

            classesEvents.postValue(Resource(newClassesEventsList, mockk()))

            assertThat(viewModel.groupedEvents.value).isEqualTo(tasksEventsGroupList)
        }

        @Test
        fun `when tasksEvents is updated and second tab is selected then groupedEvents equals to tasksEvents`() {
            viewModel.onTabSelected(1)

            val newTasksEventsGroupList: List<EventGroup> = mockk(relaxed = true)
            val newTasksEventsList: List<Event> = mockk(relaxed = true)
            every { newTasksEventsList.toGroupList() } returns newTasksEventsGroupList

            tasksEvents.postValue(Resource(newTasksEventsList, mockk()))

            assertThat(viewModel.groupedEvents.value).isEqualTo(newTasksEventsGroupList)
        }

        @Test
        fun `when tasksEvents is updated but first tab is selected then groupedEvents equals to classesEvents`() {
            viewModel.onTabSelected(0)

            val newTasksEventsGroupList: List<EventGroup> = mockk(relaxed = true)
            val newTasksEventsList: List<Event> = mockk(relaxed = true)
            every { newTasksEventsList.toGroupList() } returns newTasksEventsGroupList

            tasksEvents.postValue(Resource(newTasksEventsList, mockk()))

            assertThat(viewModel.groupedEvents.value).isEqualTo(classesEventsGroupList)
        }

    }

    @Nested
    inner class ClassesList {

        private val selectedDate = Date().plusDays(5)
        private val selectedClassList: ListBuilder<Class> = mockk()

        init {
           every { classRepo.getClassesByDate(selectedDate) }  returns selectedClassList
        }

        @Test
        fun `check initial classList corresponds to default list`() {
            assertThat(viewModel.classesList.value).isEqualTo(classList)
        }

        @Test
        fun `when date selected is provided then classList is updated with corresponding date`() {
            viewModel.onDateSelected(selectedDate)

            assertThat(viewModel.classesList.value).isEqualTo(selectedClassList)
        }

    }

    @Nested
    inner class TasksList {

        private val selectedDate = Date().plusDays(5)
        private val selectedTaskList: ListBuilder<Task> = mockk()

        init {
            every { taskRepo.getTasksByDate(selectedDate) }  returns selectedTaskList
        }

        @Test
        fun `check initial taskList corresponds to default list`() {
            assertThat(viewModel.tasksList.value).isEqualTo(taskList)
        }

        @Test
        fun `when date selected is provided then taskList is updated with corresponding date`() {
            viewModel.onDateSelected(selectedDate)

            assertThat(viewModel.tasksList.value).isEqualTo(selectedTaskList)
        }

    }
}