package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.data.entities.Subject
import dev.sarquella.studyplanner.helpers.extensions.failed
import dev.sarquella.studyplanner.helpers.extensions.progress
import dev.sarquella.studyplanner.helpers.extensions.succeed
import dev.sarquella.studyplanner.data.vo.Response


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectRepo(private val userRepo: UserRepo) {

    companion object {
        const val COLLECTION = "subjects"
    }

    fun add(subject: Subject): LiveData<Response> {
        val response = MutableLiveData<Response>()
        response.progress()
        userRepo.getCurrentUserReference().collection(COLLECTION).add(subject).addOnCompleteListener { result ->
            if (result.isSuccessful)
                response.succeed()
            else
                response.failed(result.exception?.message)
        }
        return response
    }

    fun getSubjectReference(id: String): DocumentReference =
            userRepo.getCurrentUserReference().collection(COLLECTION).document(id)

    fun getSubject(id: String): LiveData<Resource<Subject>> {
        val resource = MutableLiveData<Resource<Subject>>()
        resource.progress()
        getSubjectReference(id).get().addOnCompleteListener { result ->
                if (result.isSuccessful)
                    resource.succeed(result.result?.toObject(Subject::class.java))
                else
                    resource.failed(result.exception?.message)
            }
        return resource
    }

    fun getSubjects(): ListBuilder<Subject> =
        ListBuilder(
            userRepo.getCurrentUserReference()
                .collection(COLLECTION).orderBy("creationDate", Query.Direction.DESCENDING),
            Subject.parser()
        )

}