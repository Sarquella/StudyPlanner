package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.helpers.extensions.failed
import dev.sarquella.studyplanner.helpers.extensions.progress
import dev.sarquella.studyplanner.helpers.extensions.succeed
import dev.sarquella.studyplanner.managers.AuthManager
import dev.sarquella.studyplanner.managers.DatabaseManager


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class UserRepo(
    private val authManager: AuthManager,
    private val db: DatabaseManager
) {

    companion object {
        const val COLLECTION = "users"
    }

    fun signUp(email: String, password: String): LiveData<Response> {
        val response = MutableLiveData<Response>()
        response.progress()
        authManager.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful)
                    response.succeed()
                else
                    response.failed(result.exception?.message)
            }
        return response
    }

    fun signIn(email: String, password: String): LiveData<Response> {
        val response = MutableLiveData<Response>()
        response.progress()
        authManager.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful)
                    response.succeed()
                else
                    response.failed(result.exception?.message)
            }
        return response
    }

    fun isUserSigned() = authManager.currentUser != null

    fun getCurrentUserReference() =
        db.collection(COLLECTION).document(authManager.currentUser?.uid ?: "")
}