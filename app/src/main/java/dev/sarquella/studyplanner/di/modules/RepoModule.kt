package dev.sarquella.studyplanner.di.modules

import dev.sarquella.studyplanner.di.modules.abstractions.KoinModule
import dev.sarquella.studyplanner.repo.SubjectRepo
import dev.sarquella.studyplanner.repo.UserRepo
import org.koin.core.module.Module
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object RepoModule : KoinModule {

    override val module: Module = module {
        single { UserRepo(get()) }
        single { SubjectRepo(get()) }
    }

}