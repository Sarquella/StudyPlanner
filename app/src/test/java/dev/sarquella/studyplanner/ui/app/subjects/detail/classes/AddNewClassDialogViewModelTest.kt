package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import android.app.Application
import dev.sarquella.studyplanner.*
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.helpers.enums.ClassType
import dev.sarquella.studyplanner.helpers.extensions.addObserver
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.ClassRepo
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
class AddNewClassDialogViewModelTest {

    private val classRepo: ClassRepo = mockk(relaxed = true)
    private val application: Application = mockk(relaxed = true)
    private val viewModel: AddNewClassDialogViewModel

    private val THEORY = "Theory"
    private val INVALID_CLASS_TYPE = "Invalid class type"
    private val INVALID_START_DATE_ERROR = "Invalid start date"
    private val INVALID_END_DATE_ERROR = "Invalid end date"
    private val END_TIME_MUST_BE_LATER_THAN_START_TIME_ERROR = "End time must be later than start time"

    init {
        every { application.getString(R.string.Theory) } returns THEORY
        every { application.getString(R.string.Invalid_class_type) } returns INVALID_CLASS_TYPE
        every { application.getString(R.string.Invalid_start_date) } returns INVALID_START_DATE_ERROR
        every { application.getString(R.string.Invalid_end_date) } returns INVALID_END_DATE_ERROR
        every { application.getString(R.string.End_time_must_be_later_than_start_time) } returns
                END_TIME_MUST_BE_LATER_THAN_START_TIME_ERROR
        viewModel = AddNewClassDialogViewModel(application, classRepo)
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
        fun `if empty all fields are empty then add button is disabled`() {
            viewModel.onDayChanged("")
            viewModel.onStartTimeChanged("")
            viewModel.onEndTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only day is filled then add button is disabled`() {
            viewModel.onDayChanged(DAY)
            viewModel.onStartTimeChanged("")
            viewModel.onEndTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only startTime is filled then add button is disabled`() {
            viewModel.onDayChanged("")
            viewModel.onStartTimeChanged(START_TIME)
            viewModel.onEndTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only endTime is filled then add button is disabled`() {
            viewModel.onDayChanged("")
            viewModel.onStartTimeChanged("")
            viewModel.onEndTimeChanged(END_TIME)

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only day is empty then add button is disabled`() {
            viewModel.onDayChanged("")
            viewModel.onStartTimeChanged(START_TIME)
            viewModel.onEndTimeChanged(END_TIME)

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only startTime is empty then add button is disabled`() {
            viewModel.onDayChanged(DAY)
            viewModel.onStartTimeChanged("")
            viewModel.onEndTimeChanged(END_TIME)

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if only endTime is empty then add button is disabled`() {
            viewModel.onDayChanged(DAY)
            viewModel.onStartTimeChanged(START_TIME)
            viewModel.onEndTimeChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

        @Test
        fun `if all fields are filled then add button is enabled`() {
            viewModel.onDayChanged(DAY)
            viewModel.onStartTimeChanged(START_TIME)
            viewModel.onEndTimeChanged(END_TIME)

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
        fun `when add is called with class type then errorMessage is set and repo is not notified`() {

            viewModel.add("Invalid", INVALID_DAY, START_TIME, END_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(INVALID_CLASS_TYPE)
            verify(inverse = true) { classRepo.add(any()) }
        }

        @Test
        fun `when add is called with invalid day then errorMessage is set and repo is not notified`() {

            viewModel.add(THEORY, INVALID_DAY, START_TIME, END_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(INVALID_START_DATE_ERROR)
            verify(inverse = true) { classRepo.add(any()) }
        }

        @Test
        fun `when add is called with invalid startTime then errorMessage is set and repo is not notified`() {

            viewModel.add(THEORY, DAY, INVALID_TIME, END_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(INVALID_START_DATE_ERROR)
            verify(inverse = true) { classRepo.add(any()) }
        }

        @Test
        fun `when add is called with invalid endTime then errorMessage is set and repo is not notified`() {

            viewModel.add(THEORY, DAY, START_TIME, INVALID_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(INVALID_END_DATE_ERROR)
            verify(inverse = true) { classRepo.add(any()) }
        }

        @Test
        fun `when add is called with endTime before startTime then errorMessage is set and repo is not notified`() {

            viewModel.add(THEORY, DAY, START_TIME, BEFORE_START_TIME)

            assertThat(viewModel.errorMessage.value).isEqualTo(END_TIME_MUST_BE_LATER_THAN_START_TIME_ERROR)
            verify(inverse = true) { classRepo.add(any()) }
        }

        @Test
        fun `when add is called with correct fields then repo#add is called`() {

            viewModel.add(THEORY, DAY, START_TIME, END_TIME)

            val expectedClass = Class(
                ClassType.THEORY,
                DateUtils.parse("$DAY $START_TIME")!!,
                DateUtils.parse("$DAY $END_TIME")!!
            )

            verify { classRepo.add(expectedClass) }
        }

    }
}