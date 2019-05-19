package dev.sarquella.studyplanner.helpers.databinding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@BindingAdapter("app:textOrGone")
fun setTextOrGone(textView: TextView, text: String?) {
    text?.let {
        textView.visibility = View.VISIBLE
        textView.text = it
    } ?: run {
        textView.visibility = View.GONE
    }
}