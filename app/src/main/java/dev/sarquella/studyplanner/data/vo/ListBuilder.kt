package dev.sarquella.studyplanner.data.vo

import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.Query


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class ListBuilder<T>(val query: Query, private val snapshotParser: SnapshotParser<T>) {

    fun build(owner: LifecycleOwner): FirestoreRecyclerOptions<T> =
        FirestoreRecyclerOptions.Builder<T>()
            .setQuery(query, snapshotParser)
            .setLifecycleOwner(owner)
            .build()
}