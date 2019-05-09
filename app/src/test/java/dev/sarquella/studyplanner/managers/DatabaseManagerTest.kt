package dev.sarquella.studyplanner.managers

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
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

class DatabaseManagerTest {

    private val firestore: FirebaseFirestore = mockk(relaxed = true)
    private val dbManager = DatabaseManager(firestore)

    @Nested
    inner class Collection {

        private val collection: CollectionReference = mockk()

        @Test
        fun `when called then firestore#collection is called`() {
            val collectionPath = "collectionPath"

            dbManager.collection(collectionPath)

            verify { firestore.collection(collectionPath) }
        }

        @Test
        fun `returned collection matches firestore one`() {
            every { firestore.collection(any()) } returns collection

            val returnedCollection = dbManager.collection("collectionPath")

            assertThat(returnedCollection).isEqualTo(collection)
        }

    }
}