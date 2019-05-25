package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import dev.sarquella.studyplanner.SUBJECT_ID
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.ClassRepo
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
class ClassesViewModelTest {

    private val classRepo: ClassRepo = mockk(relaxed = true)
    private val classesList: ListBuilder<Class> = mockk()
    private val viewModel: ClassesViewModel

    init {
        every { classRepo.getClasses(SUBJECT_ID) } returns classesList
        viewModel = ClassesViewModel(SUBJECT_ID, classRepo)
    }

    @Nested
    inner class ClassList {

        @Test
        fun `check classesViewModel#classesList matches the one provided by classRepo#getClasses`() {
            assertThat(viewModel.classesList).isEqualTo(classesList)
        }

    }
}
