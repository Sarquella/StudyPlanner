package dev.sarquella.studyplanner.ui.app.subjects

import dev.sarquella.studyplanner.SUBJECT_COLOR
import dev.sarquella.studyplanner.SUBJECT_NAME
import dev.sarquella.studyplanner.data.Subject
import dev.sarquella.studyplanner.helpers.extensions.addObserver
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.SubjectRepo
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
class AddSubjectViewModelTest {

    private val subjectRepo: SubjectRepo = mockk(relaxed = true)
    private val viewModel = AddSubjectViewModel(subjectRepo)

    @Nested
    inner class IsAddButtonEnabled {

        @BeforeEach
        fun setUp() {
            viewModel.isAddButtonEnabled.addObserver()
        }

        @Test
        fun `check button is initially disabled`() {
            assertThat(viewModel.isAddButtonEnabled.value).isIn(null, false)
        }

        @Test
        fun `if all fields are filled then button is enabled`() {
            viewModel.onNameChanged(SUBJECT_NAME)

            assertThat(viewModel.isAddButtonEnabled.value).isTrue()
        }

        @Test
        fun `if name field is empty then button is disabled`() {
            viewModel.onNameChanged("")

            assertThat(viewModel.isAddButtonEnabled.value).isFalse()
        }

    }

    @Nested
    inner class Cancel {

        @Test
        fun `when called then dismiss is true`() {
            viewModel.cancel()

            assertThat(viewModel.dismiss.value).isTrue()
        }

    }

    @Nested
    inner class Add {

        @Test
        fun `when called then calls subjectRepo#add and dismisses`() {
            viewModel.add(SUBJECT_NAME, SUBJECT_COLOR)

            val subject = Subject(SUBJECT_NAME, SUBJECT_COLOR)

            verify { subjectRepo.add(subject) }
            assertThat(viewModel.dismiss.value).isTrue()
        }

    }
}