package dev.sarquella.studyplanner.ui.app.subjects.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddSubjectItemViewModel : ViewModel() {

    private val _showAddNewClassDialog = MutableLiveData<Boolean>()
    val showAddNewClassDialog: LiveData<Boolean> = _showAddNewClassDialog

    private val _showAddNewTaskDialog = MutableLiveData<Boolean>()
    val showAddNewTaskDialog: LiveData<Boolean> = _showAddNewTaskDialog

    fun addNewClass() {
        _showAddNewClassDialog.postValue(true)
    }

    fun addNewTask() {
        _showAddNewTaskDialog.postValue(true)
    }

}