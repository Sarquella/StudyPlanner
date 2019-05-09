package dev.sarquella.studyplanner.managers

import com.google.firebase.auth.FirebaseAuth


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AuthManager(private val auth: FirebaseAuth) {

    val currentUser
        get() = auth.currentUser

    fun createUserWithEmailAndPassword(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

}