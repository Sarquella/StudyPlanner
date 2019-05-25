package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

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
        itemView.tvName.text = task.name
        itemView.tvDeliveryTime.text = DateUtils.serialize(task.deliveryDate)
    }

}