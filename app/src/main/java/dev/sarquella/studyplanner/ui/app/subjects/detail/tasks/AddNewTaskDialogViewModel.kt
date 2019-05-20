package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.helpers.enums.TaskType
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import dev.sarquella.studyplanner.repo.TaskRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddNewTaskDialogViewModel(
    application: Application,
    private val taskRepo: TaskRepo
) : AndroidViewModel(application) {

    val taskTypes = TaskType.toList(application)

    private val _isAddButtonEnabled = MediatorLiveData<Boolean>()
    val isAddButtonEnabled: LiveData<Boolean> = _isAddButtonEnabled

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean> = _dismiss

    private val isNameEmpty = MutableLiveData<Boolean>()
    private val isDayEmpty = MutableLiveData<Boolean>()
    private val isTimeEmpty = MutableLiveData<Boolean>()

    init {
        _isAddButtonEnabled.addSource(isNameEmpty) {
            _isAddButtonEnabled.postValue(!it && isDayEmpty.value == false && isTimeEmpty.value == false)
        }

        _isAddButtonEnabled.addSource(isDayEmpty) {
            _isAddButtonEnabled.postValue(!it && isNameEmpty.value == false && isTimeEmpty.value == false)
        }

        _isAddButtonEnabled.addSource(isTimeEmpty) {
            _isAddButtonEnabled.postValue(!it && isNameEmpty.value == false && isDayEmpty.value == false)
        }
    }

    fun onNameChanged(name: String) {
        isNameEmpty.postValue(name.isBlank())
    }

    fun onDayChanged(day: String) {
        isDayEmpty.postValue(day.isBlank())
    }

    fun onTimeChanged(startTime: String) {
        isTimeEmpty.postValue(startTime.isBlank())
    }

    fun cancel() {
        _dismiss.postValue(true)
    }

    fun add(name: String, type: String, day: String, time: String) {

        val taskType = TaskType.parse(type, getApplication())
            ?.let { it }
            ?: run {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.Invalid_task_type))
                return
            }

        val deliveryDate = DateUtils.parse("$day $time")
            ?.let { it }
            ?: run {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.Invalid_date))
                return
            }

        taskRepo.add(Task(name, taskType, deliveryDate))
    }

}