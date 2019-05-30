package dev.sarquella.studyplanner.data.entities

import com.google.firebase.auth.FirebaseUser


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class User(val id: String, val email: String?) {

    companion object {

        fun fromFirebaseUser(firebaseUser: FirebaseUser) =
            User(firebaseUser.uid, firebaseUser.email)

    }

}