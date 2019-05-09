package dev.sarquella.studyplanner.helpers

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import android.os.Bundle




/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AndroidTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
        super.newApplication(cl, AndroidTestApp::class.java.name, context)
}