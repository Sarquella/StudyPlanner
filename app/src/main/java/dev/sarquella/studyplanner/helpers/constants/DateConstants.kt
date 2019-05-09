package dev.sarquella.studyplanner.helpers.constants

import java.text.SimpleDateFormat


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

val DATE_REGEX = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}\$".toRegex()

const val DATE_FORMAT = "dd/MM/yyyy"