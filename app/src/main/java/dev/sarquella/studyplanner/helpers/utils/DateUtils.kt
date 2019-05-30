package dev.sarquella.studyplanner.helpers.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@SuppressLint("SimpleDateFormat")
object DateUtils {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm").apply {
        isLenient = false
    }

    private val dayFormat = SimpleDateFormat("dd/MM/yyyy").apply {
        isLenient = false
    }

    private val timeFormat = SimpleDateFormat("HH:mm").apply {
        isLenient = false
    }

    fun parse(string: String): Date? =
        try {
            dateFormat.parse(string)
        } catch (e: ParseException) {
            null
        }

    fun serializeDate(date: Date): String = dateFormat.format(date)
    fun serializeDay(date: Date): String = dayFormat.format(date)
    fun serializeTime(date: Date): String = timeFormat.format(date)
}