package dev.sarquella.studyplanner.helpers.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm").apply {
        isLenient = false
    }

    fun parse(string: String): Date? =
        try {
            dateFormat.parse(string)
        } catch (e: ParseException) {
            null
        }
}