package dev.sarquella.studyplanner.services

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ApiServiceTest {

    private val firestore: FirebaseFirestore = mockk(relaxed = true)
    private val apiService = ApiService(firestore)

    @Nested
    inner class Collection {

        private val collectionPath = "collectionPath"

        @Test
        fun `when called then firestore#collection is called`() {

            apiService.collection(collectionPath)

            verify { firestore.collection(collectionPath) }
        }

        @Test
        fun `returned collection matches firestore one`() {
            val collection: CollectionReference = mockk()
            every { firestore.collection(any()) } returns collection

            val returnedCollection = apiService.collection(collectionPath)

            assertThat(returnedCollection).isEqualTo(collection)
        }

    }

    @Nested
    inner class CollectionGroup {

        private val collectionId = "collectionId"

        @Test
        fun `when called then firestore#collectionGroup is called`() {

            apiService.collectionGroup(collectionId)

            verify { firestore.collectionGroup(collectionId) }
        }

        @Test
        fun `returned query matches firestore one`() {
            val query: Query = mockk()
            every { firestore.collectionGroup(any()) } returns query

            val returnedQuery = apiService.collectionGroup(collectionId)

            assertThat(returnedQuery).isEqualTo(query)
        }

    }
}