package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.vo.Event
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.helpers.extensions.*
import dev.sarquella.studyplanner.managers.DatabaseManager


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ClassRepo(
    private val db: DatabaseManager,
    private val subjectRepo: SubjectRepo
) {

    companion object {
        const val COLLECTION = "classes"
    }

    fun add(_class: Class, subjectId: String): LiveData<Response> {
        val response = MutableLiveData<Response>()
        response.progress()
        subjectRepo.getSubjectReference(subjectId).collection(COLLECTION).add(_class).addOnCompleteListener { result ->
            if (result.isSuccessful)
                response.succeed()
            else
                response.failed(result.exception?.message)
        }
        return response
    }

    fun getClassesBySubject(subjectId: String): ListBuilder<Class> =
        ListBuilder(
            subjectRepo.getSubjectReference(subjectId)
                .collection(COLLECTION).orderBy("startDate", Query.Direction.ASCENDING),
            Class.parser
        )

    fun getClassesEvents(): LiveData<Resource<List<Event>>> {
        val resource = MutableLiveData<Resource<List<Event>>>()
        resource.progress()
        db.collectionGroup(COLLECTION).addSnapshotListener { snapshot, exception ->
            if (exception == null) {
                val classes = snapshot?.toClassList() ?: listOf()
                resource.succeed(classes.retrieveEventList())
            } else {
                resource.failed(exception.message)
            }
        }
        return resource
    }

}