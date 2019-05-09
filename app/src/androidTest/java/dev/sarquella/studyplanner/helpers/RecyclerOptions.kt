package dev.sarquella.studyplanner.helpers

import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.ClassSnapshotParser
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import io.mockk.every
import io.mockk.mockk


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class RecyclerOptions<T>(
    private val owner: LifecycleOwner,
    modelClass: Class<T>
) {

    private var list: MutableList<T> = mutableListOf()
    private val documentList: MutableList<DocumentSnapshot>
        get() = list.mapIndexed { index, _ ->
            mockk<DocumentSnapshot>(relaxed = true).also {
                every { it.id } returns index.toString()
            }
        }.toMutableList()

    private val parser = object : ClassSnapshotParser<T>(modelClass) {
        override fun parseSnapshot(snapshot: DocumentSnapshot): T = list[snapshot.id.toInt()]
    }

    private val snapshot = object : ObservableSnapshotArray<T>(parser) {
        override fun getSnapshots(): MutableList<DocumentSnapshot> = documentList
    }

    private fun build() = FirestoreRecyclerOptions.Builder<T>()
        .setSnapshotArray(snapshot)
        .setLifecycleOwner(owner)
        .build()

    fun withNoItems() = withItems(mutableListOf())

    fun withItems(list: MutableList<T>): FirestoreRecyclerOptions<T> {
        this.list = list
        return build()
    }

}