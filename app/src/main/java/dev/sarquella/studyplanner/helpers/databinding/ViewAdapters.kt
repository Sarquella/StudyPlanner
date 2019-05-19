package dev.sarquella.studyplanner.helpers.databinding

import android.view.View
import androidx.databinding.BindingAdapter


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@BindingAdapter("app:onTouch")
fun setOnTouch(view: View, onTouch: () -> Unit) {
    view.setOnTouchListener { _, _ ->
        onTouch.invoke()
        false
    }
}