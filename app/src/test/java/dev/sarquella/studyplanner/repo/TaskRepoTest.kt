package dev.sarquella.studyplanner.repo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.SUBJECT_ID
import dev.sarquella.studyplanner.TASK
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class TaskRepoTest {

    private val subjectRepo: SubjectRepo = mockk()
    private val taskRepo = TaskRepo(subjectRepo)

    private val subjectRef: DocumentReference = mockk(relaxed = true)

    init {
        every { subjectRepo.getSubjectReference(SUBJECT_ID) } returns subjectRef
    }

    @Nested
    inner class Collection {

        @Test
        fun `check collection has correct name`() {
            assertThat(TaskRepo.COLLECTION).isEqualTo("tasks")
        }

    }

    @Nested
    inner class Add {
        private val docTask: com.google.android.gms.tasks.Task<DocumentReference> = mockk()
        private val onCompleteListener = slot<OnCompleteListener<DocumentReference>>()

        init {
            every { subjectRef.collection(TaskRepo.COLLECTION).add(any()) } returns docTask
            every { docTask.addOnCompleteListener(capture(onCompleteListener)) } returns docTask
        }

        @Test
        fun `when called then subjectRef#collection#add is called`() {
            taskRepo.add(TASK, SUBJECT_ID)

            verify { subjectRef.collection(TaskRepo.COLLECTION).add(TASK) }
        }

        @Test
        fun `initial response state is progress`() {
            val response = taskRepo.add(TASK, SUBJECT_ID)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.PROGRESS))
        }

        @Test
        fun `when succeed then response state is succeed`() {
            every { docTask.isSuccessful } returns true
            val response = taskRepo.add(TASK, SUBJECT_ID)
            onCompleteListener.captured.onComplete(docTask)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.SUCCEED))
        }

        @Test
        fun `when failed then response state is failed with error message`() {
            val errorMessage = "Error message"
            every { docTask.isSuccessful } returns false
            every { docTask.exception } returns Exception(errorMessage)
            val response = taskRepo.add(TASK, SUBJECT_ID)
            onCompleteListener.captured.onComplete(docTask)

            assertThat(response.value).isEqualTo(
                Response(
                    Response.ResponseState.FAILED,
                    errorMessage
                )
            )
        }
    }

    @Nested
    inner class GetTasks {

        private val collectionRef: CollectionReference = mockk(relaxed = true)

        init {
            every { subjectRef.collection(TaskRepo.COLLECTION) } returns collectionRef
        }

        @Test
        fun `when called returns ListBuilder with sorted subjects query`() {
            val listBuilder = taskRepo.getTasks(SUBJECT_ID)

            val expected = ListBuilder(
                collectionRef.orderBy("deliveryDate", Query.Direction.ASCENDING),
                Task.parser
            )

            assertThat(listBuilder).isEqualTo(expected)
        }

    }
}