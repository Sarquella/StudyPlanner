package dev.sarquella.studyplanner.data.vo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

data class Response(val state: ResponseState = ResponseState.IDLE,
                    val message: String? = null) {
    enum class ResponseState {
        IDLE,
        PROGRESS,
        SUCCEED,
        FAILED
    }
}