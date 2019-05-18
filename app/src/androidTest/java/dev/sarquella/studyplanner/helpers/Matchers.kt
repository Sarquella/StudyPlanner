package dev.sarquella.studyplanner.helpers

import android.graphics.Color
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.card.MaterialCardView
import org.hamcrest.Description
import org.hamcrest.Matcher


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun hasBackgroundColor(color: String?): Matcher<View> =
    object : BoundedMatcher<View, MaterialCardView>(MaterialCardView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("has background color")
        }

        override fun matchesSafely(item: MaterialCardView?): Boolean =
            item?.cardBackgroundColor?.defaultColor == Color.parseColor(color)

    }

fun withTitle(title: String?): Matcher<View> =
    object : BoundedMatcher<View, CollapsingToolbarLayout>(CollapsingToolbarLayout::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("has title")
        }

        override fun matchesSafely(item: CollapsingToolbarLayout?): Boolean =
            item?.title?.equals(title) ?: false

    }

fun withRecyclerView(id: Int) = RecyclerViewMatcher(id)