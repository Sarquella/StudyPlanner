package dev.sarquella.studyplanner

import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.entities.Subject
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.helpers.enums.ClassType
import dev.sarquella.studyplanner.helpers.enums.TaskType
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

const val EMAIL = "email@example.com"

const val PASSWORD = "password"


const val SUBJECT_NAME = "Subject"
const val SUBJECT_COLOR = "#3398DB"
const val SUBJECT_ID = "subject_id"

val SUBJECT = Subject(SUBJECT_NAME, SUBJECT_COLOR, SUBJECT_ID)

val SUBJECT_2 = Subject(SUBJECT_NAME + "_2", "#000000", SUBJECT_ID + "_2")


const val DAY = "23/06/2018"
const val START_TIME = "14:30"
const val END_TIME = "16:30"

const val INVALID_DAY = "99/99/9999"
const val INVALID_TIME = "25:00"
const val BEFORE_START_TIME = "12:30"

val CLASS_TYPE = ClassType.THEORY
val CLASS = Class(
    CLASS_TYPE,
    DateUtils.parse("$DAY $START_TIME") ?: Date(),
    DateUtils.parse("$DAY $END_TIME") ?: Date(),
    SUBJECT.name,
    SUBJECT.color
)

val CLASS_2 = Class(
    ClassType.PRACTICE,
    DateUtils.parse("$DAY $BEFORE_START_TIME") ?: Date(),
    DateUtils.parse("$DAY $START_TIME") ?: Date(),
    SUBJECT_2.name,
    SUBJECT_2.color
)

const val TASK_NAME = "Task"
val TASK_TYPE = TaskType.PRACTICE
val TASK = Task(
    TASK_NAME,
    TASK_TYPE,
    DateUtils.parse("$DAY $START_TIME") ?: Date(),
    SUBJECT.name,
    SUBJECT.color
)

val TASK_2 = Task(
    TASK_NAME + "_2",
    TaskType.EXAM,
    DateUtils.parse("$DAY $END_TIME") ?: Date(),
    SUBJECT_2.name,
    SUBJECT_2.color
)