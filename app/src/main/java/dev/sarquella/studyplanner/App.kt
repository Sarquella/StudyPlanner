package dev.sarquella.studyplanner

import android.app.Application
import dev.sarquella.studyplanner.di.Injector


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //Inits dependency injection
        Injector.start(this)
    }

}