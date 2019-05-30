package dev.sarquella.studyplanner.data.entities

import com.firebase.ui.firestore.ClassSnapshotParser
import dev.sarquella.studyplanner.helpers.enums.ClassType
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class Class(
    val type: ClassType = ClassType.THEORY,
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val subjectName: String = "",
    val subjectColor: String = "#FFFFFF"
) {

    companion object {
        val parser = ClassSnapshotParser(Class::class.java)
    }

}