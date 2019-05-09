package dev.sarquella.studyplanner.helpers.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import io.mockk.mockk


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun LiveData<*>.addObserver() {
    val lifecycleOwner: LifecycleOwner = mockk()
    val lifecycle = LifecycleRegistry(lifecycleOwner)
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    this.observe({ lifecycle }) {}
}