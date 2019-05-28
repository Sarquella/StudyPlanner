package dev.sarquella.studyplanner.ui.app.calendar

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.data.vo.Event
import dev.sarquella.studyplanner.data.vo.EventGroup
import dev.sarquella.studyplanner.helpers.extensions.toGroupList
import dev.sarquella.studyplanner.repo.ClassRepo
import dev.sarquella.studyplanner.repo.TaskRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class CalendarViewModel(classRepo: ClassRepo, taskRepo: TaskRepo) : ViewModel() {

    private val classesEvents = classRepo.getClassesEvents()
    private val tasksEvents = taskRepo.getTasksEvents()

    private val currentTab = MutableLiveData<Int>()

    private val _groupedEvents = MediatorLiveData<List<EventGroup>>()
    val groupedEvents: LiveData<List<EventGroup>> = _groupedEvents

    init {

        fun selectCurrentEvents() {
            _groupedEvents.postValue(
                if (currentTab.value == 1)
                    tasksEvents.value?.item?.toGroupList() ?: listOf()
                else
                    classesEvents.value?.item?.toGroupList() ?: listOf()
            )

        }

        _groupedEvents.addSource(classesEvents) {
            selectCurrentEvents()
        }

        _groupedEvents.addSource(tasksEvents) {
            selectCurrentEvents()
        }

        _groupedEvents.addSource(currentTab) {
            selectCurrentEvents()
        }
    }

    fun onTabSelected(position: Int) {
        currentTab.postValue(position)
    }
}