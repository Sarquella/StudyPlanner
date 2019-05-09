package dev.sarquella.studyplanner.helpers

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.card.MaterialCardView
import org.hamcrest.Description
import org.hamcrest.Matcher


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun <VH : RecyclerView.ViewHolder> withAdapter(adapter: RecyclerView.Adapter<VH>): Matcher<View> =
    object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("matches adapter")
        }

        override fun matchesSafely(item: RecyclerView?): Boolean =
            item?.adapter == adapter

    }

fun hasBackgroundColor(color: String?): Matcher<View> =
    object : BoundedMatcher<View, MaterialCardView>(MaterialCardView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("has background color")
        }

        override fun matchesSafely(item: MaterialCardView?): Boolean =
            item?.cardBackgroundColor?.defaultColor == Color.parseColor(color)

    }