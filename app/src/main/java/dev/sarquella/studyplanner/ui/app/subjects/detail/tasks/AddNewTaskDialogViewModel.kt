package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.helpers.enums.TaskType


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddNewTaskDialogViewModel(application: Application) : AndroidViewModel(application) {

    val taskTypes = TaskType.toList(application)

    private val _isAddButtonEnabled = MediatorLiveData<Boolean>()
    val isAddButtonEnabled: LiveData<Boolean> = _isAddButtonEnabled

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean> = _dismiss

    fun onNameChanged(day: String) {

    }

    fun onDayChanged(day: String) {

    }

    fun onTimeChanged(startTime: String) {

    }

    fun cancel() {

    }

    fun add(name: String, type: String, day: String, time: String) {

    }

}