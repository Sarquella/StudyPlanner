package dev.sarquella.studyplanner.helpers.extensions

import com.google.firebase.firestore.QuerySnapshot
import dev.sarquella.studyplanner.data.entities.Class
import dev.sarquella.studyplanner.data.entities.Task


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */
 
fun QuerySnapshot.toClassList(): List<Class> =
        this.map { Class.parser.parseSnapshot(it) }

fun QuerySnapshot.toTaskList(): List<Task> =
        this.map { Task.parser.parseSnapshot(it) }