package dev.sarquella.studyplanner.ui.app.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.sarquella.studyplanner.R


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SubjectDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_subject_detail, container, false)

    companion object {

        private const val SUBJECT_ID = "subjectId"

        fun newInstance(subjectId: String) = SubjectDetailFragment().apply {
            arguments = Bundle().apply {
                putString(SUBJECT_ID, subjectId)
            }
        }
    }
}