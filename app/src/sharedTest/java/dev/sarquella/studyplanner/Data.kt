package dev.sarquella.studyplanner

import dev.sarquella.studyplanner.data.entities.Subject
import dev.sarquella.studyplanner.helpers.enums.ClassType


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


const val DAY = "23/06/2018"
const val START_TIME = "14:30"
const val END_TIME = "16:30"

const val INVALID_DAY = "99/99/9999"
const val INVALID_TIME = "25:00"
const val BEFORE_START_TIME = "12:30"

val CLASS_TYPE = ClassType.THEORY

const val TASK_NAME = "Task"