package dev.sarquella.studyplanner.data.entities

import dev.sarquella.studyplanner.helpers.enums.ClassType
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class Class(
    val type: ClassType = ClassType.THEORY,
    val startDate: Date = Date(),
    val endDate: Date = Date()
)