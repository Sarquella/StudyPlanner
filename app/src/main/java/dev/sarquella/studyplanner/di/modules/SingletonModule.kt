package dev.sarquella.studyplanner.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.sarquella.studyplanner.di.modules.abstractions.KoinModule
import dev.sarquella.studyplanner.services.AuthService
import dev.sarquella.studyplanner.services.ApiService
import org.koin.core.module.Module
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object SingletonModule : KoinModule {

    override val module: Module = module {
        single { FirebaseAuth.getInstance() }
        single { AuthService(get()) }

        single { FirebaseFirestore.getInstance() }
        single { ApiService(get()) }
    }

}