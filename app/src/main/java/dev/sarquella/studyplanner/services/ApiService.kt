package dev.sarquella.studyplanner.services

import com.google.firebase.firestore.FirebaseFirestore


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ApiService(private val firestore: FirebaseFirestore) {

    fun collection(collectionPath: String) = firestore.collection(collectionPath)

    fun collectionGroup(collectionId: String) = firestore.collectionGroup(collectionId)

}