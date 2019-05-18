package dev.sarquella.studyplanner.ui.app.subjects.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.repo.SubjectRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectDetailViewModel(
    subjectId: String,
    subjectRepo: SubjectRepo
) : ViewModel() {

    private val subject = subjectRepo.getSubject(subjectId)

    val subjectName: LiveData<String> = Transformations.map(subject) {
        it.item?.name ?: ""
    }
}