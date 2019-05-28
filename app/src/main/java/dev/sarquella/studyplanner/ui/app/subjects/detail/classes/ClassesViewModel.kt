package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.repo.ClassRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ClassesViewModel(
    subjectId: String,
    classRepo: ClassRepo
) : ViewModel() {

    val classesList = classRepo.getClassesBySubject(subjectId)

}