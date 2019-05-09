package dev.sarquella.studyplanner.ui.app.subjects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.repo.SubjectRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectsViewModel(subjectRepo: SubjectRepo) : ViewModel() {

    private val _showAddSubjectDialog = MutableLiveData<Boolean>()
    val showAddSubjectDialog: LiveData<Boolean> = _showAddSubjectDialog

    val subjectsList = subjectRepo.getSubjects()

    fun showAddSubjectDialog() {
        _showAddSubjectDialog.postValue(true)
    }

}