package dev.sarquella.studyplanner.ui.app.listing.classes

import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassListAdapter
import dev.sarquella.studyplanner.ui.app.listing.classes.ClassViewHolder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ClassListAdapterTest {

    private val options: FirestoreRecyclerOptions<Class> = mockk()
    private val adapter: ClassListAdapter

    private val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
    private val snapshot: ObservableSnapshotArray<Class> = mockk()

    init {
        every { options.owner } returns lifecycleOwner
        every { options.snapshots } returns snapshot

        adapter = ClassListAdapter(options)
    }

    @Nested
    inner class OnBindViewHolder {

        @Test
        fun `when called then binds item to viewHolder`() {

            val _class: Class = mockk()
            every { snapshot[any()] } returns _class

            val viewHolder: ClassViewHolder = mockk(relaxUnitFun = true)

            adapter.onBindViewHolder(viewHolder, 0)

            verify { viewHolder.bind(_class) }
        }

    }

}