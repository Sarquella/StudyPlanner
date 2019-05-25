package dev.sarquella.studyplanner.data.entities

import dev.sarquella.studyplanner.helpers.enums.TaskType
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class Task(
    val name: String = "",
    val type: TaskType = TaskType.PRACTICE,
    val deliveryDate: Date = Date()
)