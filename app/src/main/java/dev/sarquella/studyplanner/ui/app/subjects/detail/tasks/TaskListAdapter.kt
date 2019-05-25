package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.data.entities.Task


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class TaskListAdapter(options: FirestoreRecyclerOptions<Task>) :
    FirestoreRecyclerAdapter<Task, TaskViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_task, parent, false)
        )

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int, item: Task) {
        viewHolder.bind(item)
    }

}