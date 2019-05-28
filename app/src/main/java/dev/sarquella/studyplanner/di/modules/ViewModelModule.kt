package dev.sarquella.studyplanner.di.modules

import dev.sarquella.studyplanner.di.modules.abstractions.KoinModule
import dev.sarquella.studyplanner.ui.app.calendar.CalendarViewModel
import dev.sarquella.studyplanner.ui.app.subjects.AddSubjectViewModel
import dev.sarquella.studyplanner.ui.app.subjects.SubjectsViewModel
import dev.sarquella.studyplanner.ui.app.subjects.detail.AddSubjectItemViewModel
import dev.sarquella.studyplanner.ui.app.subjects.detail.SubjectDetailViewModel
import dev.sarquella.studyplanner.ui.app.subjects.detail.classes.AddNewClassDialogViewModel
import dev.sarquella.studyplanner.ui.app.subjects.detail.classes.ClassesViewModel
import dev.sarquella.studyplanner.ui.app.subjects.detail.tasks.AddNewTaskDialogViewModel
import dev.sarquella.studyplanner.ui.app.subjects.detail.tasks.TasksViewModel
import dev.sarquella.studyplanner.ui.launch.LaunchViewModel
import dev.sarquella.studyplanner.ui.sign.signIn.SignInViewModel
import dev.sarquella.studyplanner.ui.sign.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidApplication
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

        viewModel { CalendarViewModel(get(), get()) }

        viewModel { SubjectsViewModel(get()) }
        viewModel { AddSubjectViewModel(get()) }

        viewModel { (subjectId: String) -> SubjectDetailViewModel(subjectId, get()) }
        viewModel { AddSubjectItemViewModel() }

        viewModel { (subjectId: String) -> AddNewClassDialogViewModel(androidApplication(), subjectId, get()) }
        viewModel { (subjectId: String) -> AddNewTaskDialogViewModel(androidApplication(), subjectId, get()) }

        viewModel { (subjectId: String) -> ClassesViewModel(subjectId, get()) }
        viewModel { (subjectId: String) -> TasksViewModel(subjectId, get()) }
    }

}