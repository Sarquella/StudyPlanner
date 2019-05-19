package dev.sarquella.studyplanner.rules

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.test.rule.ActivityTestRule


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class DialogTestRule<F : DialogFragment> :
    ActivityTestRule<FragmentActivity>(FragmentActivity::class.java, true, true) {

    var testDialog: F? = null

    fun setDialog(dialog: F) {
        testDialog = dialog
        with(activity) {
            dialog.show(supportFragmentManager, "TEST_DIALOG")
        }
    }
}