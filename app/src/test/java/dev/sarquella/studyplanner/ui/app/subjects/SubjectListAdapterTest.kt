package dev.sarquella.studyplanner.ui.app.subjects

import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import dev.sarquella.studyplanner.data.entities.Subject
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectListAdapterTest {

    private val options: FirestoreRecyclerOptions<Subject> = mockk()
    private val adapter: SubjectListAdapter

    private val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
    private val snapshot: ObservableSnapshotArray<Subject> = mockk()

    init {
        every { options.owner } returns lifecycleOwner
        every { options.snapshots } returns snapshot

        adapter = SubjectListAdapter(options)
    }

    @Nested
    inner class OnBindViewHolder {

        @Test
        fun `when called then binds item to viewHolder`() {

            val subject: Subject = mockk()
            every { snapshot[any()] } returns subject

            val viewHolder: SubjectViewHolder = mockk(relaxUnitFun = true)

            adapter.onBindViewHolder(viewHolder, 0)

            verify { viewHolder.bind(subject) }
        }

    }

}