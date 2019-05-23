package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import android.app.Application
import dev.sarquella.studyplanner.*
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.helpers.enums.TaskType
import dev.sarquella.studyplanner.helpers.extensions.addObserver
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.TaskRepo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class AddNewTaskDialogViewModelTest {

    private val taskRepo: TaskRepo = mockk(relaxed = true)
    private val application: Application = mockk(relaxed = true)
    private val viewModel: AddNewTaskDialogViewModel

    private val PRACTICE = "Practice"
    private val INVALID_TASK_TYPE = "Invalid task type"
    private val INVALID_DATE_ERROR = "Invalid date"

    init {
        every { application.getString(R.string.Practice) } returns PRACTICE
        every { application.getString(R.string.Invalid_task_type) } returns INVALID_TASK_TYPE
        every { application.getString(R.string.Invalid_date) } returns INVALID_DATE_ERROR

        viewModel = AddNewTaskDialogViewModel(application, SUBJECT_ID, taskRepo)
    }

    @Nested
    inner class IsAddButtonEnabled {

        @BeforeEach
        fun setUp() {
            viewModel.isAddButtonEnabled.addObserver()
        }

        @Test
        fun `initial state is not true`() {
            assertThat(viewModel.isAddButtonEnabled.value).isIn(null, false)
        }

        @Test
        fun `if all fields are empty then add button is disabled`() {
            viewModel.onNameChanged("")
            viewModel.onDayChanged("")
            viewModel.onTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only name is filled then add button is disabled`() {
            viewModel.onNameChanged(TASK_NAME)
            viewModel.onDayChanged("")
            viewModel.onTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only day is filled then add button is disabled`() {
            viewModel.onNameChanged("")
            viewModel.onDayChanged(DAY)
            viewModel.onTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only time is filled then add button is disabled`() {
            viewModel.onNameChanged("")
            viewModel.onDayChanged("")
            viewModel.onTimeChanged(START_TIME)

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only name is empty then add button is disabled`() {
            viewModel.onNameChanged("")
            viewModel.onDayChanged(DAY)
            viewModel.onTimeChanged(START_TIME)

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only day is empty then add button is disabled`() {
            viewModel.onNameChanged(TASK_NAME)
            viewModel.onDayChanged("")
            viewModel.onTimeChanged(START_TIME)

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only time is empty then add button is disabled`() {
            viewModel.onNameChanged(TASK_NAME)
            viewModel.onDayChanged(DAY)
            viewModel.onTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if all fields are filled then add button is enabled`() {
            viewModel.onNameChanged(TASK_NAME)
            viewModel.onDayChanged(DAY)
            viewModel.onTimeChanged(START_TIME)

            assertThat(viewModel.isAddButtonEnabled.value).isTrue()
        }
    }

    @Nested
    inner class Dismiss {

        @Test
        fun `initial state is not true`() {
            assertThat(viewModel.dismiss.value).isIn(null, false)
        }

        @Test
        fun `when cancel is called then dismiss is true`() {
            viewModel.cancel()

            assertThat(viewModel.dismiss.value).isTrue()
        }

    }

    @Nested
    inner class Add {

        @Test
        fun `when add is called with invalid task type then errorMessage is set and repo is not notified`() {

            viewModel.add(TASK_NAME, "Invalid", DAY, START_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(INVALID_TASK_TYPE)
            verify(inverse = true) { taskRepo.add(any(), any()) }
            assertThat(viewModel.dismiss.value).isIn(null, false)
        }

        @Test
        fun `when add is called with invalid day then errorMessage is set and repo is not notified`() {

            viewModel.add(TASK_NAME, PRACTICE, INVALID_DAY, START_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(INVALID_DATE_ERROR)
            verify(inverse = true) { taskRepo.add(any(), any()) }
            assertThat(viewModel.dismiss.value).isIn(null, false)
        }

        @Test
        fun `when add is called with invalid startTime then errorMessage is set and repo is not notified`() {

            viewModel.add(TASK_NAME, PRACTICE, DAY, INVALID_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(INVALID_DATE_ERROR)
            verify(inverse = true) { taskRepo.add(any(), any()) }
            assertThat(viewModel.dismiss.value).isIn(null, false)
        }

        @Test
        fun `when add is called with correct fields then repo#add is called and dialog dismissed`() {

            viewModel.add(TASK_NAME, PRACTICE, DAY, START_TIME)

            val expectedTask = Task(
                TASK_NAME,
                TaskType.PRACTICE,
                DateUtils.parse("$DAY $START_TIME")!!
            )

            verify { taskRepo.add(expectedTask, SUBJECT_ID) }
            assertThat(viewModel.dismiss.value).isTrue()
        }
    }
}