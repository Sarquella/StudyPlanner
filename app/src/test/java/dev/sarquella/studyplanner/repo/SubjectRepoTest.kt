package dev.sarquella.studyplanner.repo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.data.ListBuilder
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.managers.DatabaseManager
import dev.sarquella.studyplanner.data.Response
import dev.sarquella.studyplanner.data.Subject
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class SubjectRepoTest {

    private val dbManager: DatabaseManager = mockk()
    private val subjectRepo = SubjectRepo(dbManager)

    private val collection: CollectionReference = mockk()

    init {
        every { dbManager.collection(SubjectRepo.COLLECTION) } returns collection
    }

    @Nested
    inner class Collection {

        @Test
        fun `check collection has correct name`() {
            assertThat(SubjectRepo.COLLECTION).isEqualTo("subjects")
        }

    }

    @Nested
    inner class Add {
        private val docTask: Task<DocumentReference> = mockk()
        private val onCompleteListener = slot<OnCompleteListener<DocumentReference>>()

        init {
            every { collection.add(any()) } returns docTask
            every { docTask.addOnCompleteListener(capture(onCompleteListener)) } returns docTask
        }

        @Test
        fun `when called then dbManager#db#collection#add is called`() {
            subjectRepo.add(SUBJECT)

            verifySequence {
                dbManager.collection(SubjectRepo.COLLECTION)
                collection.add(SUBJECT)
            }
        }

        @Test
        fun `initial response state is progress`() {
            val response = subjectRepo.add(SUBJECT)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.PROGRESS))
        }

        @Test
        fun `if add succeed then response state is succeed`() {
            every { docTask.isSuccessful } returns true
            val response = subjectRepo.add(SUBJECT)
            onCompleteListener.captured.onComplete(docTask)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.SUCCEED))
        }

        @Test
        fun `if add failed then response state is failed with error message`() {
            val errorMessage = "Error message"
            every { docTask.isSuccessful } returns false
            every { docTask.exception } returns Exception(errorMessage)
            val response = subjectRepo.add(SUBJECT)
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
    inner class GetSubjects {

        private val query: Query = mockk()

        init {
            every { collection.orderBy(any<String>(), any()) } returns query
        }

        @Test
        fun `when called returns ListBuilder with sorted subjects query`() {
            val listBuilder = subjectRepo.getSubjects()

            val expected = ListBuilder(
                collection.orderBy("creationDate", Query.Direction.DESCENDING),
                Subject::class.java
            )

            assertThat(listBuilder).isEqualTo(expected)
        }

    }
}