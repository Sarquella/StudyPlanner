package dev.sarquella.studyplanner.di

import android.content.Context
import dev.sarquella.studyplanner.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object Injector {
    fun start(context: Context) {
        startKoin {
            androidContext(context)
            modules(
                listOf(
                    AdapterModule.module,
                    FactoryModule.module,
                    RepoModule.module,
                    SingletonModule.module,
                    ViewModelModule.module
                )
            )
        }
    }

}