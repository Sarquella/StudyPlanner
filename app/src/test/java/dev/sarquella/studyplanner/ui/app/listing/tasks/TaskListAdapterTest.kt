package dev.sarquella.studyplanner.ui.app.listing.tasks

import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import dev.sarquella.studyplanner.data.entities.Task
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskListAdapter
import dev.sarquella.studyplanner.ui.app.listing.tasks.TaskViewHolder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class TaskListAdapterTest {

    private val options: FirestoreRecyclerOptions<Task> = mockk()
    private val adapter: TaskListAdapter

    private val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
    private val snapshot: ObservableSnapshotArray<Task> = mockk()

    init {
        every { options.owner } returns lifecycleOwner
        every { options.snapshots } returns snapshot

        adapter = TaskListAdapter(options)
    }

    @Nested
    inner class OnBindViewHolder {

        @Test
        fun `when called then binds item to viewHolder`() {

            val task: Task = mockk()
            every { snapshot[any()] } returns task

            val viewHolder: TaskViewHolder = mockk(relaxUnitFun = true)

            adapter.onBindViewHolder(viewHolder, 0)

            verify { viewHolder.bind(task) }
        }

    }

}