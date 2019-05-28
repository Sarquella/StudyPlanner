package dev.sarquella.studyplanner.ui.app.calendar

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class EventSpan(private val colors: List<Int>, private val radius: Float = DEFAULT_RADIUS) : LineBackgroundSpan {

    companion object {
        private const val DEFAULT_RADIUS = 8f
    }

    constructor(color: Int, radius: Float = DEFAULT_RADIUS) : this(listOf(color), radius)

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        charSequence: CharSequence,
        start: Int,
        end: Int,
        lineNum: Int
    ) {

        var leftMost = (colors.size - 1) * -10

        colors.forEach { color ->
            val background = paint.color
            paint.color = color
            canvas.drawCircle((left + right).toFloat() / 2 - leftMost, bottom + radius, radius, paint)
            paint.color = background
            leftMost += 20
        }
    }
}