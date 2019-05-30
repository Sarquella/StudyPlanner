package dev.sarquella.studyplanner.data.entities

import com.firebase.ui.firestore.ClassSnapshotParser
import dev.sarquella.studyplanner.helpers.enums.TaskType
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class Task(
    val name: String = "",
    val type: TaskType = TaskType.PRACTICE,
    val deliveryDate: Date = Date(),
    val subjectName: String = "",
    val subjectColor: String = "#FFFFFF"
) {

    companion object {
        val parser = ClassSnapshotParser(Task::class.java)
    }

}