package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.helpers.extensions.failed
import dev.sarquella.studyplanner.helpers.extensions.progress
import dev.sarquella.studyplanner.helpers.extensions.succeed


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class TaskRepo(private val subjectRepo: SubjectRepo) {

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

    fun getTasks(subjectId: String): ListBuilder<Task> =
        ListBuilder(
            subjectRepo.getSubjectReference(subjectId)
                .collection(COLLECTION).orderBy("deliveryDate", Query.Direction.ASCENDING),
            Task.parser
        )

}