package dev.sarquella.studyplanner.ui.app.subjects.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _showAddItemDialog = MutableLiveData<Boolean>()
    val showAddItemDialog: LiveData<Boolean> = _showAddItemDialog

    fun showAddItemDialog() {
        _showAddItemDialog.postValue(true)
    }
}