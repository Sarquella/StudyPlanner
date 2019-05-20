package dev.sarquella.studyplanner.helpers.extensions

import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.data.vo.Resource
import dev.sarquella.studyplanner.data.vo.Response


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

fun <T> MutableLiveData<Resource<T>>.progress() {
    this.postValue(
        Resource(
            null,
            Response(Response.ResponseState.PROGRESS)
        )
    )
}

fun <T> MutableLiveData<Resource<T>>.succeed(item: T?, message: String? = null) {
    this.postValue(
        Resource(
            item,
            Response(
                Response.ResponseState.SUCCEED,
                message
            )
        )
    )
}

fun <T> MutableLiveData<Resource<T>>.failed(message: String? = null) {
    this.postValue(
        Resource(
            null,
            Response(
                Response.ResponseState.FAILED,
                message
            )
        )
    )
}