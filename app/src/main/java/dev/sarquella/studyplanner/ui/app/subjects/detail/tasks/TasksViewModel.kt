package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.repo.TaskRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class TasksViewModel(
    subjectId: String,
    taskRepo: TaskRepo
) : ViewModel() {

    val tasksList = taskRepo.getTasks(subjectId)

}