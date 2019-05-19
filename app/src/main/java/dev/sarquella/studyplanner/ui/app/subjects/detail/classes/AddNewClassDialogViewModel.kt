package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import dev.sarquella.studyplanner.helpers.enums.ClassType


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddNewClassDialogViewModel(application: Application) : AndroidViewModel(application) {

    val classTypes = ClassType.toList(application)

    private val _isAddButtonEnabled = MediatorLiveData<Boolean>()
    val isAddButtonEnabled: LiveData<Boolean> = _isAddButtonEnabled

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean> = _dismiss

    fun onDayChanged(day: String) {

    }

    fun onStartTimeChanged(startTime: String) {

    }

    fun onEndTimeChanged(endTime: String) {

    }

    fun cancel() {

    }

    fun add(type: String, day: String, startTime: String, endTime: String) {

    }

}