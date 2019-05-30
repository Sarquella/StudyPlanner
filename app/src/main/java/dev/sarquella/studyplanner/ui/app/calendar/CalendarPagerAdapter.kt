package dev.sarquella.studyplanner.ui.app.calendar

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.calendar.classes.DailyClassesFragment
import dev.sarquella.studyplanner.ui.app.calendar.tasks.DailyTasksFragment


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class CalendarPagerAdapter(
    context: Context?,
    fragmentManager: FragmentManager?
) : FragmentPagerAdapter(fragmentManager) {

    private inner class Page(val title: String?, val fragment: Fragment)

    private val pages = listOf(
        Page(context?.getString(R.string.Classes),
            DailyClassesFragment()
        ),
        Page(context?.getString(R.string.Tasks), DailyTasksFragment())
    )

    override fun getItem(position: Int): Fragment = pages[position].fragment

    override fun getPageTitle(position: Int): CharSequence? = pages[position].title

    override fun getCount(): Int = pages.count()

}