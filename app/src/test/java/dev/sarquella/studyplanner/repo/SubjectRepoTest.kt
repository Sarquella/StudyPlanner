package dev.sarquella.studyplanner.repo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.SUBJECT
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.data.entities.Subject
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

    private val userRepo: UserRepo = mockk()
    private val subjectRepo = SubjectRepo(userRepo)

    private val userRef: DocumentReference = mockk(relaxed = true)

    init {
        every { userRepo.getCurrentUserReference() } returns userRef
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
            every { userRef.collection(SubjectRepo.COLLECTION).add(any()) } returns docTask
            every { docTask.addOnCompleteListener(capture(onCompleteListener)) } returns docTask
        }

        @Test
        fun `when called then userRef#collection#add is called`() {
            subjectRepo.add(SUBJECT)

            verify { userRef.collection(SubjectRepo.COLLECTION).add(SUBJECT) }
        }

        @Test
        fun `initial response state is progress`() {
            val response = subjectRepo.add(SUBJECT)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.PROGRESS))
        }

        @Test
        fun `when succeed then response state is succeed`() {
            every { docTask.isSuccessful } returns true
            val response = subjectRepo.add(SUBJECT)
            onCompleteListener.captured.onComplete(docTask)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.SUCCEED))
        }

        @Test
        fun `when failed then response state is failed with error message`() {
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
    inner class GetSubjectReference {

        private val subjectId = SUBJECT.id

        @Test
        fun `when called then userRef#collection#document is called`() {
            subjectRepo.getSubjectReference(subjectId)

            verify { userRef.collection(SubjectRepo.COLLECTION).document(subjectId) }
        }

        @Test
        fun `returned document reference matches the one returned by firestore`() {
            val expectedRef: DocumentReference = mockk()
            every { userRef.collection(SubjectRepo.COLLECTION).document(any()) } returns expectedRef

            val subjectRef = subjectRepo.getSubjectReference(subjectId)

            assertThat(subjectRef).isEqualTo(expectedRef)
        }
    }

    @Nested
    inner class GetSubject {

        private val subjectId = SUBJECT.id

        private val docTask: Task<DocumentSnapshot> = mockk()
        private val onCompleteListener = slot<OnCompleteListener<DocumentSnapshot>>()

        init {
            every { userRef.collection(SubjectRepo.COLLECTION).document(subjectId).get() } returns docTask
            every { docTask.addOnCompleteListener(capture(onCompleteListener)) } returns docTask
        }

        @Test
        fun `when called then userRef#collection#document#get is called`() {
            subjectRepo.getSubject(subjectId)

            verify { userRef.collection(SubjectRepo.COLLECTION).document(subjectId).get() }
        }

        @Test
        fun `initial response state is progress`() {
            val response = subjectRepo.getSubject(subjectId)

            assertThat(response.value)
                .isEqualTo(
                    Resource<Subject>(
                        null,
                        Response(Response.ResponseState.PROGRESS)
                    )
                )
        }

        @Test
        fun `when succeed then response state is succeed and resource contains item`() {
            every { docTask.isSuccessful } returns true
            every { docTask.result?.toObject(Subject::class.java) } returns SUBJECT

            val response = subjectRepo.getSubject(subjectId)
            onCompleteListener.captured.onComplete(docTask)

            assertThat(response.value).isEqualTo(
                Resource(
                    SUBJECT,
                    Response(Response.ResponseState.SUCCEED)
                )
            )
        }

        @Test
        fun `when failed then response state is failed with error message`() {
            val errorMessage = "Error message"
            every { docTask.isSuccessful } returns false
            every { docTask.exception } returns Exception(errorMessage)

            val response = subjectRepo.getSubject(subjectId)
            onCompleteListener.captured.onComplete(docTask)

            assertThat(response.value).isEqualTo(
                Resource(
                    null,
                    Response(
                        Response.ResponseState.FAILED,
                        errorMessage
                    )
                )
            )
        }
    }

    @Nested
    inner class GetSubjects {

        private val collectionRef: CollectionReference = mockk(relaxed = true)

        init {
            every { userRef.collection(SubjectRepo.COLLECTION) } returns collectionRef
        }

        @Test
        fun `when called returns ListBuilder with sorted subjects query`() {
            val listBuilder = subjectRepo.getSubjects()

            val expected = ListBuilder(
                collectionRef.orderBy("creationDate", Query.Direction.DESCENDING),
                Subject.parser
            )

            assertThat(listBuilder).isEqualTo(expected)
        }

    }
}