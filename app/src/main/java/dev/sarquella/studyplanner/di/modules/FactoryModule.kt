package dev.sarquella.studyplanner.di.modules

import dev.sarquella.studyplanner.data.vo.EventGroup
import dev.sarquella.studyplanner.di.modules.abstractions.KoinModule
import dev.sarquella.studyplanner.ui.app.calendar.EventDecorator
import org.koin.core.module.Module
import org.koin.dsl.module


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

object FactoryModule : KoinModule {

    override val module: Module = module {
        factory { (eventGroup: EventGroup) -> EventDecorator(eventGroup) }
    }

}