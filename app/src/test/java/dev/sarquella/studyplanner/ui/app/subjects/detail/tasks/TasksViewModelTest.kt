package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import dev.sarquella.studyplanner.SUBJECT_ID
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.TaskRepo
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class TasksViewModelTest {

    private val taskRepo: TaskRepo = mockk(relaxed = true)
    private val tasksList: ListBuilder<Task> = mockk()
    private val viewModel: TasksViewModel

    init {
        every { taskRepo.getTasks(SUBJECT_ID) } returns tasksList
        viewModel = TasksViewModel(SUBJECT_ID, taskRepo)
    }

    @Nested
    inner class TasksList {

        @Test
        fun `check tasksViewModel#tasksList matches the one provided by taskRepo#getTasks`() {
            assertThat(viewModel.tasksList).isEqualTo(tasksList)
        }

    }
}