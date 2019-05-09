package dev.sarquella.studyplanner.ui.app.subjects

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.sarquella.studyplanner.data.Subject
import kotlinx.android.synthetic.main.viewholder_subject.view.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(subject: Subject) {
        itemView.tvName.text = subject.name
        itemView.colorIndicator.setCardBackgroundColor(Color.parseColor(subject.color))
    }

}