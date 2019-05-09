package dev.sarquella.studyplanner.helpers.extensions

import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.data.Response


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun MutableLiveData<Response>.progress() {
    this.postValue(Response(Response.ResponseState.PROGRESS))
}

fun MutableLiveData<Response>.succeed(message: String? = null) {
    this.postValue(
        Response(
            Response.ResponseState.SUCCEED,
            message
        )
    )
}

fun MutableLiveData<Response>.failed(message: String? = null) {
    this.postValue(
        Response(
            Response.ResponseState.FAILED,
            message
        )
    )
}