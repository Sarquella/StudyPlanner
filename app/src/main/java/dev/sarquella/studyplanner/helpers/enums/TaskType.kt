package dev.sarquella.studyplanner.helpers.enums

import android.content.Context
import androidx.annotation.StringRes
import dev.sarquella.studyplanner.R


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

enum class TaskType(@StringRes val value: Int) {
    PRACTICE(R.string.Practice),
    SEMINAR(R.string.Seminar),
    EXAM(R.string.Exam);

    companion object {
        fun toList(context: Context) =
            listOf(
                context.getString(PRACTICE.value),
                context.getString(SEMINAR.value),
                context.getString(EXAM.value)
            )

        fun parse(string: String, context: Context): TaskType? {
            values().forEach { type ->
                if (context.getString(type.value) == string)
                    return type
            }
            return null
        }
    }
}