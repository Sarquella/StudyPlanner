package dev.sarquella.studyplanner.ui.app.subjects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.data.Subject
import dev.sarquella.studyplanner.repo.SubjectRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddSubjectViewModel(private val subjectRepo: SubjectRepo) : ViewModel() {

    private val _isAddButtonEnabled = MutableLiveData<Boolean>()
    val isAddButtonEnabled: LiveData<Boolean> = _isAddButtonEnabled

    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean> = _dismiss

    fun onNameChanged(text: String) {
        _isAddButtonEnabled.postValue(!text.isNullOrBlank())
    }

    fun cancel() {
        _dismiss.postValue(true)
    }

    fun add(name: String, color: String) {
        subjectRepo.add(Subject(name, color))
        _dismiss.postValue(true)
    }
}