package dev.sarquella.studyplanner.ui.app.subjects.detail

import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.data.entities.Subject
import dev.sarquella.studyplanner.helpers.extensions.addObserver
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.SubjectRepo
import io.mockk.every
import io.mockk.mockk
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
class SubjectDetailViewModelTest {

    private val subjectRepo: SubjectRepo = mockk(relaxed = true)
    private val viewModel: SubjectDetailViewModel

    private val subjectResource = MutableLiveData<Resource<Subject>>()

    init {
        every { subjectRepo.getSubject(any()) } returns subjectResource
        viewModel = SubjectDetailViewModel(SUBJECT.id, subjectRepo)
    }

    @Nested
    inner class SubjectName {

        @BeforeEach
        fun setUp() {
            viewModel.subjectName.addObserver()
        }

        @Test
        fun `when subject is provided then subjectName matches its name`() {
            val resource: Resource<Subject> = mockk()
            every { resource.item } returns SUBJECT

            subjectResource.postValue(resource)

            assertThat(viewModel.subjectName.value).isEqualTo(SUBJECT.name)
        }

        @Test
        fun `when subject is not provided then subjectName is empty`() {
            val resource: Resource<Subject> = mockk()
            every { resource.item } returns null

            subjectResource.postValue(resource)

            assertThat(viewModel.subjectName.value).isEmpty()
        }

    }

    @Nested
    inner class ShowAddSubjectItemDialog {

        @Test
        fun `check showAddSubjectItemDialog is not true initially`() {
            assertThat(viewModel.showAddItemDialog.value).isIn(null, false)
        }

        @Test
        fun `when called then showAddSubjectItemDialog is true`() {
            viewModel.showAddItemDialog()

            assertThat(viewModel.showAddItemDialog.value).isTrue()
        }
    }
}