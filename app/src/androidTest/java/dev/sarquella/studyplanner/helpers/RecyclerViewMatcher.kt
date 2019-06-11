package dev.sarquella.studyplanner.helpers

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import android.content.res.Resources.NotFoundException
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description) {
                val idDescription = resources?.let { res ->
                    try {
                        res.getResourceName(recyclerViewId)
                    } catch (exception: NotFoundException) {
                        "$recyclerViewId not found"
                    }
                } ?: run {
                    recyclerViewId.toString()
                }

                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {

                this.resources = view.resources

                if(childView == null) {
                    val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
                    childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                }

                return view == childView?.findViewById(targetViewId)
            }
        }
}