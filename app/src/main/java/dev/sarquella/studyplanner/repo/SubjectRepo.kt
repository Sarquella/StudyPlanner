package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.data.ListBuilder
import dev.sarquella.studyplanner.data.Resource
import dev.sarquella.studyplanner.data.Subject
import dev.sarquella.studyplanner.helpers.extensions.failed
import dev.sarquella.studyplanner.helpers.extensions.progress
import dev.sarquella.studyplanner.helpers.extensions.succeed
import dev.sarquella.studyplanner.managers.DatabaseManager
import dev.sarquella.studyplanner.data.Response


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectRepo(private val db: DatabaseManager) {

    companion object {
        const val COLLECTION = "subjects"
    }

    fun add(subject: Subject): LiveData<Response> {
        val response = MutableLiveData<Response>()
        response.progress()
        db.collection(SubjectRepo.COLLECTION).add(subject).addOnCompleteListener { result ->
            if (result.isSuccessful)
                response.succeed()
            else
                response.failed(result.exception?.message)
        }
        return response
    }

    fun getSubject(id: String): LiveData<Resource<Subject>> {
        val resource = MutableLiveData<Resource<Subject>>()
        resource.progress()
        db.collection(SubjectRepo.COLLECTION).document(id).get().addOnCompleteListener { result ->
            if (result.isSuccessful)
                resource.succeed(result.result?.toObject(Subject::class.java))
            else
                resource.failed(result.exception?.message)
        }
        return resource
    }

    fun getSubjects(): ListBuilder<Subject> =
        ListBuilder(
            db.collection(SubjectRepo.COLLECTION).orderBy("creationDate", Query.Direction.DESCENDING),
            SnapshotParser { snapshot ->
                Subject(
                    snapshot.getString("name"),
                    snapshot.getString("color"),
                    snapshot.reference.id
                )
            }
        )

}