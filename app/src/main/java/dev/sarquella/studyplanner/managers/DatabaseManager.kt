package dev.sarquella.studyplanner.managers

import com.google.firebase.firestore.FirebaseFirestore


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class DatabaseManager(private val db: FirebaseFirestore) {

    fun collection(collectionPath: String) = db.collection(collectionPath)

    fun collectionGroup(collectionId: String) = db.collectionGroup(collectionId)

}