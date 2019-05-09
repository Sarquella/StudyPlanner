package dev.sarquella.studyplanner.data

import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class ListBuilder<T>(val query: Query, private val itemType: Class<T>) {

    fun build(owner: LifecycleOwner): FirestoreRecyclerOptions<T> =
        FirestoreRecyclerOptions.Builder<T>()
            .setQuery(query, itemType)
            .setLifecycleOwner(owner)
            .build()
}