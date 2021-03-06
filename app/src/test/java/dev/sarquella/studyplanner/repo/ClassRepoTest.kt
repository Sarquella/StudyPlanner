package dev.sarquella.studyplanner.repo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import dev.sarquella.studyplanner.CLASS
import dev.sarquella.studyplanner.SUBJECT_ID
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.vo.Event
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.helpers.extensions.plusDays
import dev.sarquella.studyplanner.helpers.extensions.retrieveEventList
import dev.sarquella.studyplanner.helpers.extensions.toClassList
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.services.ApiService
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class ClassRepoTest {

    private val apiService: ApiService = mockk(relaxed = true)
    private val subjectRepo: SubjectRepo = mockk()
    private val classRepo = ClassRepo(apiService, subjectRepo)

    private val subjectRef: DocumentReference = mockk(relaxed = true)

    init {
        every { subjectRepo.getSubjectReference(SUBJECT_ID) } returns subjectRef
    }

    @Nested
    inner class Collection {

        @Test
        fun `check collection has correct name`() {
            assertThat(ClassRepo.COLLECTION).isEqualTo("classes")
        }

    }

    @Nested
    inner class Add {
        private val docTask: Task<DocumentReference> = mockk()
        private val onCompleteListener = slot<OnCompleteListener<DocumentReference>>()

        init {
            every { subjectRef.collection(ClassRepo.COLLECTION).add(any()) } returns docTask
            every { docTask.addOnCompleteListener(capture(onCompleteListener)) } returns docTask
        }

        @Test
        fun `when called then subjectRef#collection#add is called`() {
            classRepo.add(CLASS, SUBJECT_ID)

            verify { subjectRef.collection(ClassRepo.COLLECTION).add(CLASS) }
        }

        @Test
        fun `initial response state is progress`() {
            val response = classRepo.add(CLASS, SUBJECT_ID)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.PROGRESS))
        }

        @Test
        fun `when succeed then response state is succeed`() {
            every { docTask.isSuccessful } returns true
            val response = classRepo.add(CLASS, SUBJECT_ID)
            onCompleteListener.captured.onComplete(docTask)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.SUCCEED))
        }

        @Test
        fun `when failed then response state is failed with error message`() {
            val errorMessage = "Error message"
            every { docTask.isSuccessful } returns false
            every { docTask.exception } returns Exception(errorMessage)
            val response = classRepo.add(CLASS, SUBJECT_ID)
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
    inner class GetClassesBySubject {

        private val collectionRef: CollectionReference = mockk(relaxed = true)

        init {
            every { subjectRef.collection(ClassRepo.COLLECTION) } returns collectionRef
        }

        @Test
        fun `when called returns ListBuilder with sorted classes query`() {
            val listBuilder = classRepo.getClassesBySubject(SUBJECT_ID)

            val expected = ListBuilder(
                collectionRef.orderBy("startDate", Query.Direction.ASCENDING),
                Class.parser
            )

            assertThat(listBuilder).isEqualTo(expected)
        }

    }

    @Nested
    inner class GetTasksByDate {

        @Test
        fun `when called returns ListBuilder with filtered classes query`() {
            val date = Date()
            val listBuilder = classRepo.getClassesByDate(date)

            val expected = ListBuilder(
                apiService.collectionGroup(ClassRepo.COLLECTION)
                    .whereGreaterThanOrEqualTo("startDate", date)
                    .whereLessThan("startDate", date.plusDays(1)),
                Class.parser
            )

            assertThat(listBuilder).isEqualTo(expected)
        }

    }

    @Nested
    inner class GetClassesEvents {

        private val query: Query = mockk()
        private val eventListener = slot<EventListener<QuerySnapshot>>()

        init {
            mockkStatic("dev.sarquella.studyplanner.helpers.extensions.QuerySnapshotKt")
            mockkStatic("dev.sarquella.studyplanner.helpers.extensions.ListClassKt")

            every { apiService.collectionGroup(ClassRepo.COLLECTION) } returns query
            every { query.addSnapshotListener(capture(eventListener))} returns mockk()
        }


        @Test
        fun `when called then apiService#collectionGroup is called`() {
            classRepo.getClassesEvents()

            verify { apiService.collectionGroup(ClassRepo.COLLECTION) }
        }

        @Test
        fun `initial response state is progress`() {
            val response = classRepo.getClassesEvents()

            assertThat(response.value)
                .isEqualTo(
                    Resource<List<Event>>(
                        null,
                        Response(Response.ResponseState.PROGRESS)
                    )
                )
        }

        @Test
        fun `when succeed then response state is succeed and resource contains items`() {
            val snapshot: QuerySnapshot = mockk(relaxed = true)
            val events: List<Event> = mockk(relaxed = true)

            every { snapshot.toClassList().retrieveEventList()} returns events

            val response = classRepo.getClassesEvents()
            eventListener.captured.onEvent(snapshot, null)

            assertThat(response.value).isEqualTo(
                Resource(
                    events,
                    Response(Response.ResponseState.SUCCEED)
                )
            )
        }

        @Test
        fun `when failed then response state is failed with error message`() {
            val errorMessage = "Error message"
            val exception: FirebaseFirestoreException = mockk()
            every { exception.message } returns errorMessage

            val response = classRepo.getClassesEvents()
            eventListener.captured.onEvent(null, exception)

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
}