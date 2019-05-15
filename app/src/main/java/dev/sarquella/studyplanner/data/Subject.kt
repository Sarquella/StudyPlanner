package dev.sarquella.studyplanner.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class Subject(
    val name: String? = "",
    val color: String? = "#FFFFFF",
    @Exclude
    val id: String = ""
) {
    @ServerTimestamp
    var creationDate: Date? = null
}