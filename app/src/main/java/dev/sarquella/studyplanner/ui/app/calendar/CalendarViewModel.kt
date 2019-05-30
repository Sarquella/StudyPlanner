package dev.sarquella.studyplanner.ui.app.calendar

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.Event
import dev.sarquella.studyplanner.data.vo.EventGroup
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.helpers.extensions.toGroupList
import dev.sarquella.studyplanner.repo.ClassRepo
import dev.sarquella.studyplanner.repo.TaskRepo
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class CalendarViewModel(
    private val classRepo: ClassRepo,
    private val taskRepo: TaskRepo
) : ViewModel() {

    enum class Tab {
        CLASSES,
        TASKS
    }

    private val classesEvents = classRepo.getClassesEvents()
    private val tasksEvents = taskRepo.getTasksEvents()

    private val currentTab = MutableLiveData<Tab>()

    private val _groupedEvents = MediatorLiveData<List<EventGroup>>()
    val groupedEvents: LiveData<List<EventGroup>> = _groupedEvents

    private val _classesList = MutableLiveData<ListBuilder<Class>>(classRepo.getClassesByDate(Date()))
    val classesList: LiveData<ListBuilder<Class>> = _classesList

    private val _tasksList = MutableLiveData<ListBuilder<Task>>(taskRepo.getTasksByDate(Date()))
    val tasksList: LiveData<ListBuilder<Task>> = _tasksList

    init {

        fun selectCurrentEvents() {
            _groupedEvents.postValue(
                if (currentTab.value == Tab.TASKS)
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
        currentTab.postValue(
            if (position == 0)
                Tab.CLASSES
            else
                Tab.TASKS
        )
    }

    fun onDateSelected(date: Date) {
        _classesList.postValue(classRepo.getClassesByDate(date))
        _tasksList.postValue(taskRepo.getTasksByDate(date))
    }
}