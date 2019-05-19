package dev.sarquella.studyplanner.ui.app.subjects.detail

import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class AddSubjectItemViewModelTest {

    private val viewModel = AddSubjectItemViewModel()

    @Nested
    inner class AddNewClass {

        @Test
        fun `check showAddNewClassDialog is not true initially`() {
            assertThat(viewModel.showAddNewClassDialog.value).isIn(null, false)
        }

        @Test
        fun `when called then showAddNewClassDialog is true`() {
            viewModel.addNewClass()

            assertThat(viewModel.showAddNewClassDialog.value).isTrue()
        }
    }

    @Nested
    inner class AddNewTask {

        @Test
        fun `check showAddNewTaskDialog is not true initially`() {
            assertThat(viewModel.showAddNewTaskDialog.value).isIn(null, false)
        }

        @Test
        fun `when called then showAddNewTaskDialog is true`() {
            viewModel.addNewTask()

            assertThat(viewModel.showAddNewTaskDialog.value).isTrue()
        }
    }
}