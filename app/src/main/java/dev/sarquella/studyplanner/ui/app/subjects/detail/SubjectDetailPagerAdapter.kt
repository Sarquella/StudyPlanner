package dev.sarquella.studyplanner.ui.app.subjects.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.subjects.detail.classes.ClassesFragment
import dev.sarquella.studyplanner.ui.app.subjects.detail.tasks.TasksFragment


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectDetailPagerAdapter(
    subjectId: String,
    context: Context?,
    fragmentManager: FragmentManager?
) : FragmentPagerAdapter(fragmentManager) {

    private inner class Page(val title: String?, val fragment: Fragment)

    private val pages = listOf(
        Page(context?.getString(R.string.Classes), ClassesFragment.newInstance(subjectId)),
        Page(context?.getString(R.string.Tasks), TasksFragment.newInstance(subjectId))
    )

    override fun getItem(position: Int): Fragment = pages[position].fragment

    override fun getPageTitle(position: Int): CharSequence? = pages[position].title

    override fun getCount(): Int = pages.count()

}