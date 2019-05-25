package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.entities.Class


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ClassListAdapter(options: FirestoreRecyclerOptions<Class>) :
    FirestoreRecyclerAdapter<Class, ClassViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder =
        ClassViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_class, parent, false)
        )

    override fun onBindViewHolder(viewHolder: ClassViewHolder, position: Int, item: Class) {
        viewHolder.bind(item)
    }

}