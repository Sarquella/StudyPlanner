package dev.sarquella.studyplanner.services

import com.google.firebase.auth.FirebaseAuth


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AuthService(private val auth: FirebaseAuth) {

    val currentUser
        get() = auth.currentUser

    fun createUserWithEmailAndPassword(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun signOut() = auth.signOut()

}