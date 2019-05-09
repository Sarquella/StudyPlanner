package dev.sarquella.studyplanner.helpers.extensions


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun Int.toHexColor(): String = "#" + Integer.toHexString(this).removeRange(0, 2).toUpperCase()