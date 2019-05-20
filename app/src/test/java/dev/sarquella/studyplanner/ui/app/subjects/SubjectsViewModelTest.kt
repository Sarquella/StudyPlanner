package dev.sarquella.studyplanner.ui.app.subjects

import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.entities.Subject
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.SubjectRepo
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
class SubjectsViewModelTest {

    private val subjectRepo: SubjectRepo = mockk(relaxed = true)

    @Nested
    inner class ShowAddSubjectDialog {

        private val viewModel = SubjectsViewModel(subjectRepo)

        @Test
        fun `check showAddSubjectDialog is not true initially`() {
            assertThat(viewModel.showAddSubjectDialog.value).isIn(null, false)
        }

        @Test
        fun `when called then showAddSubjectDialog is true`() {
            viewModel.showAddSubjectDialog()

            assertThat(viewModel.showAddSubjectDialog.value).isTrue()
        }

    }

    @Nested
    inner class SubjectList {

        private val subjectsList: ListBuilder<Subject> = mockk()
        private val viewModel: SubjectsViewModel

        init {
            every { subjectRepo.getSubjects() } returns subjectsList
            viewModel = SubjectsViewModel(subjectRepo)
        }

        @Test
        fun `check subjectsViewModel#subjectList matches the one provided by subjectRepo#getSubjects`() {
            assertThat(viewModel.subjectsList).isEqualTo(subjectsList)
        }

    }
}