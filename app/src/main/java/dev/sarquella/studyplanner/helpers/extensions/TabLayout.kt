package dev.sarquella.studyplanner.helpers.extensions

import com.google.android.material.tabs.TabLayout


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun TabLayout.onTabSelected(callback: (Int) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}
        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                callback(it.position)
            }
        }

    })
}