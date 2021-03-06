package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.Event
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.helpers.extensions.*
import dev.sarquella.studyplanner.services.ApiService
import java.util.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class TaskRepo(
    private val apiService: ApiService,
    private val subjectRepo: SubjectRepo
) {

    companion object {
        const val COLLECTION = "tasks"
    }

    fun add(task: Task, subjectId: String): LiveData<Response> {
        val response = MutableLiveData<Response>()
        response.progress()
        subjectRepo.getSubjectReference(subjectId).collection(COLLECTION).add(task).addOnCompleteListener { result ->
            if (result.isSuccessful)
                response.succeed()
            else
                response.failed(result.exception?.message)
        }
        return response
    }

    fun getTasksBySubject(subjectId: String): ListBuilder<Task> =
        ListBuilder(
            subjectRepo.getSubjectReference(subjectId)
                .collection(COLLECTION).orderBy("deliveryDate", Query.Direction.ASCENDING),
            Task.parser
        )

    fun getTasksByDate(selectedDate: Date): ListBuilder<Task> =
            ListBuilder(
                apiService.collectionGroup(COLLECTION)
                    .whereGreaterThanOrEqualTo("deliveryDate", selectedDate)
                    .whereLessThan("deliveryDate", selectedDate.plusDays(1)),
                Task.parser
            )

    fun getTasksEvents(): LiveData<Resource<List<Event>>> {
        val resource = MutableLiveData<Resource<List<Event>>>()
        resource.progress()
        apiService.collectionGroup(COLLECTION).addSnapshotListener { snapshot, exception ->
            if (exception == null) {
                val tasks = snapshot?.toTaskList() ?: listOf()
                resource.succeed(tasks.retrieveEventList())
            } else {
                resource.failed(exception.message)
            }
        }
        return resource
    }
}