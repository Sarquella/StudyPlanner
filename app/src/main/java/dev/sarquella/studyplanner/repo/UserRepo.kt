package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.data.entities.User
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.helpers.extensions.failed
import dev.sarquella.studyplanner.helpers.extensions.progress
import dev.sarquella.studyplanner.helpers.extensions.succeed
import dev.sarquella.studyplanner.services.AuthService
import dev.sarquella.studyplanner.services.ApiService


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class UserRepo(
    private val authService: AuthService,
    private val apiService: ApiService
) {

    companion object {
        const val COLLECTION = "users"
    }

    fun signUp(email: String, password: String): LiveData<Response> {
        val response = MutableLiveData<Response>()
        response.progress()
        authService.createUserWithEmailAndPassword(email, password)
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
        authService.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful)
                    response.succeed()
                else
                    response.failed(result.exception?.message)
            }
        return response
    }

    fun signOut() {
        authService.signOut()
    }

    fun isUserSigned() = authService.currentUser != null

    fun getCurrentUser() = authService.currentUser?.let {
        User.fromFirebaseUser(it)
    } ?: run {
        null
    }

    fun getCurrentUserReference() =
        apiService.collection(COLLECTION).document(authService.currentUser?.uid ?: "")
}