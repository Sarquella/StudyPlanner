package dev.sarquella.studyplanner.ui.app.subjects

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.Subject


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectListAdapter(options: FirestoreRecyclerOptions<Subject>) :
    FirestoreRecyclerAdapter<Subject, SubjectViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder =
        SubjectViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_subject, parent, false)
        )

    override fun onBindViewHolder(viewHolder: SubjectViewHolder, position: Int, item: Subject) {
        viewHolder.bind(item)
    }

}