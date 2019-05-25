package dev.sarquella.studyplanner.ui.app.subjects.detail.classes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.helpers.utils.DateUtils
import kotlinx.android.synthetic.main.viewholder_class.view.*


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(_class: Class) {
        itemView.tvType.text = itemView.context.getString(_class.type.value)
        itemView.tvStartTime.text = DateUtils.serialize(_class.startDate)
        itemView.tvEndTime.text = DateUtils.serialize(_class.endDate)
    }

}