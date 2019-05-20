package dev.sarquella.studyplanner.data.entities

import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class Subject(
    val name: String = "",
    val color: String = "#FFFFFF",
    @get:Exclude
    val id: String = ""
) {
    @ServerTimestamp
    var creationDate: Date? = null

    companion object {
        fun parser() = SnapshotParser { snapshot ->
            fromSnapshot(
                snapshot
            )
        }

        private fun fromSnapshot(snapshot: DocumentSnapshot) =
            Subject(
                snapshot.getString("name") ?: "",
                snapshot.getString("color") ?: "#FFFFFF",
                snapshot.reference.id
            )
    }
}