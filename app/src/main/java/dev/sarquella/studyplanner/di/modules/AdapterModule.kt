package dev.sarquella.studyplanner.di.modules

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.entities.Subject
import dev.sarquella.studyplanner.di.modules.abstractions.KoinModule
import dev.sarquella.studyplanner.ui.app.subjects.SubjectListAdapter
import dev.sarquella.studyplanner.ui.app.subjects.detail.classes.ClassListAdapter
import org.koin.core.module.Module
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object AdapterModule : KoinModule {

    override val module: Module = module {
        factory { (options: FirestoreRecyclerOptions<Subject>) -> SubjectListAdapter(options) }
        factory { (options: FirestoreRecyclerOptions<Class>) -> ClassListAdapter(options) }
    }

}