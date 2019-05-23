package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import android.app.Application
import androidx.lifecycle.*
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.helpers.enums.ClassType
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import dev.sarquella.studyplanner.repo.ClassRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddNewClassDialogViewModel(
    application: Application,
    private val subjectId: String,
    private val classRepo: ClassRepo
) : AndroidViewModel(application) {

    val classTypes = ClassType.toList(application)

    private val _isAddButtonEnabled = MediatorLiveData<Boolean>()
    val isAddButtonEnabled: LiveData<Boolean> = _isAddButtonEnabled

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean> = _dismiss

    private val isDayEmpty = MutableLiveData<Boolean>()
    private val isStartTimeEmpty = MutableLiveData<Boolean>()
    private val isEndTimeEmpty = MutableLiveData<Boolean>()

    init {
        _isAddButtonEnabled.addSource(isDayEmpty) {
            _isAddButtonEnabled.postValue(!it && isStartTimeEmpty.value == false && isEndTimeEmpty.value == false)
        }

        _isAddButtonEnabled.addSource(isStartTimeEmpty) {
            _isAddButtonEnabled.postValue(!it && isDayEmpty.value == false && isEndTimeEmpty.value == false)
        }

        _isAddButtonEnabled.addSource(isEndTimeEmpty) {
            _isAddButtonEnabled.postValue(!it && isDayEmpty.value == false && isStartTimeEmpty.value == false)
        }
    }

    fun onDayChanged(day: String) {
        isDayEmpty.postValue(day.isBlank())
    }

    fun onStartTimeChanged(startTime: String) {
        isStartTimeEmpty.postValue(startTime.isBlank())
    }

    fun onEndTimeChanged(endTime: String) {
        isEndTimeEmpty.postValue(endTime.isBlank())
    }

    fun cancel() {
        _dismiss.postValue(true)
    }

    fun add(type: String, day: String, startTime: String, endTime: String) {

        val classType = ClassType.parse(type, getApplication())
            ?.let { it }
            ?: run {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.Invalid_class_type))
                return
            }

        val startDate = DateUtils.parse("$day $startTime")
            ?.let { it }
            ?: run {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.Invalid_start_date))
                return
            }

        val endDate = DateUtils.parse("$day $endTime")
            ?.let { it }
            ?: run {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.Invalid_end_date))
                return
            }

        if (!endDate.after(startDate)) {
            _errorMessage
                .postValue(getApplication<Application>().getString(R.string.End_time_must_be_later_than_start_time))
            return
        }

        classRepo.add(Class(classType, startDate, endDate), subjectId)
        cancel()
    }

}