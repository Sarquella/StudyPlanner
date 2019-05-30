package dev.sarquella.studyplanner.ui.app.listing.tasks

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import kotlinx.android.synthetic.main.viewholder_task.view.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(task: Task) {
        itemView.tvType.text = itemView.context.getString(task.type.value)
        itemView.tvSubject.text = task.subjectName
        itemView.colorIndicator.setCardBackgroundColor(Color.parseColor(task.subjectColor))
        itemView.tvName.text = task.name
        itemView.tvDate.text = DateUtils.serializeDate(task.deliveryDate)
    }

}