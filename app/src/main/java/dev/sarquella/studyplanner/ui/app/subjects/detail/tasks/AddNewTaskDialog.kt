package dev.sarquella.studyplanner.ui.app.subjects.detail.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.ui.app.subjects.detail.abstractions.FullTransparentDialogFragment


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AddNewTaskDialog : FullTransparentDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_new_task, container, false)
    }
}