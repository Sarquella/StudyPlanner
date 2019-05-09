package dev.sarquella.studyplanner.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.sarquella.studyplanner.di.modules.abstractions.KoinModule
import dev.sarquella.studyplanner.managers.AuthManager
import dev.sarquella.studyplanner.managers.DatabaseManager
import org.koin.core.module.Module
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object SingletonModule : KoinModule {

    override val module: Module = module {
        single { FirebaseAuth.getInstance() }
        single { AuthManager(get()) }

        single { FirebaseFirestore.getInstance() }
        single { DatabaseManager(get()) }
    }

}