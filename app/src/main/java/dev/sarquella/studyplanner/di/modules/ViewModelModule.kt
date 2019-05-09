package dev.sarquella.studyplanner.di.modules

import dev.sarquella.studyplanner.di.modules.abstractions.KoinModule
import dev.sarquella.studyplanner.ui.app.subjects.AddSubjectViewModel
import dev.sarquella.studyplanner.ui.app.subjects.SubjectsViewModel
import dev.sarquella.studyplanner.ui.launch.LaunchViewModel
import dev.sarquella.studyplanner.ui.sign.signIn.SignInViewModel
import dev.sarquella.studyplanner.ui.sign.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object ViewModelModule : KoinModule {

    override val module: Module = module {
        viewModel { LaunchViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
        viewModel { SignInViewModel(get()) }

        viewModel { SubjectsViewModel(get()) }
        viewModel { AddSubjectViewModel(get()) }
    }

}