package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.firestore.ClassSnapshotParser
import com.google.firebase.firestore.Query
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.vo.ListBuilder
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.helpers.extensions.failed
import dev.sarquella.studyplanner.helpers.extensions.progress
import dev.sarquella.studyplanner.helpers.extensions.succeed


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ClassRepo(private val subjectRepo: SubjectRepo) {

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

    fun getClasses(subjectId: String): ListBuilder<Class> =
        ListBuilder(
            subjectRepo.getSubjectReference(subjectId)
                .collection(COLLECTION).orderBy("startDate", Query.Direction.ASCENDING),
            Class.parser
        )

}