package dev.sarquella.studyplanner.helpers.enums

import android.content.Context
import androidx.annotation.StringRes
import dev.sarquella.studyplanner.R


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

enum class ClassType(@StringRes val value: Int) {
    THEORY(R.string.Theory),
    PRACTICE(R.string.Practice),
    SEMINAR(R.string.Seminar);

    companion object {
        fun toList(context: Context) =
            listOf(
                context.getString(THEORY.value),
                context.getString(PRACTICE.value),
                context.getString(SEMINAR.value)
            )

        fun parse(string: String, context: Context): ClassType? {
            values().forEach { type ->
                if (context.getString(type.value) == string)
                    return type
            }
            return null
        }
    }
}