package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.data.Response
import dev.sarquella.studyplanner.helpers.extensions.failed
import dev.sarquella.studyplanner.helpers.extensions.progress
import dev.sarquella.studyplanner.helpers.extensions.succeed
import dev.sarquella.studyplanner.managers.AuthManager


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class UserRepo(private val authManager: AuthManager) {

    fun isUserSigned() = authManager.currentUser != null

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

}