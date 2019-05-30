package dev.sarquella.studyplanner.rules

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.test.rule.ActivityTestRule
import dev.sarquella.studyplanner.R
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.android.synthetic.main.activity_main.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class FragmentTestRule<F : Fragment> :
    ActivityTestRule<FragmentActivity>(FragmentActivity::class.java, true, true) {

    var testFragment: F? = null

    fun setFragment(fragment: F, navController: NavController? = null) {
        testFragment = fragment
        with(activity) {
            this.runOnUiThread {
                fragment.viewLifecycleOwnerLiveData.observe(activity, Observer {
                    if (lifecycle.currentState == Lifecycle.State.RESUMED)
                        Navigation.setViewNavController(fragment.view!!, navController)
                })
                this.supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, fragment, "TEST_FRAGMENT")
                    .commit()
            }
        }
    }
}