package dev.sarquella.studyplanner.ui.sign.abstractions

import androidx.lifecycle.*
import dev.sarquella.studyplanner.repo.UserRepo
import dev.sarquella.studyplanner.data.vo.Response


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

abstract class SignViewModel(protected val userRepo: UserRepo) : ViewModel() {

    data class AuthState(val status: Status, val message: String? = null) {
        enum class Status {
            AUTHENTICATING,
            AUTHENTICATED,
            INVALID_AUTHENTICATION
        }
    }

    private val _navToNext = MutableLiveData<Boolean>()
    val navToNext: LiveData<Boolean> = _navToNext

    private val _isButtonEnabled = MediatorLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val isEmailEmpty = MutableLiveData<Boolean>()
    private val isPasswordEmpty = MutableLiveData<Boolean>()

    private val _authState = MediatorLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        _isButtonEnabled.addSource(isEmailEmpty) {
            _isButtonEnabled.postValue(!it && isPasswordEmpty.value == false)
        }

        _isButtonEnabled.addSource(isPasswordEmpty) {
            _isButtonEnabled.postValue(!it && isEmailEmpty.value == false)
        }
    }

    abstract fun sign(email: String, password: String)

    fun navToNext() {
        _navToNext.postValue(true)
    }

    fun onEmailChanged(email: String) {
        isEmailEmpty.postValue(email.isBlank())
    }

    fun onPasswordChanged(password: String) {
        isPasswordEmpty.postValue(password.isBlank())
    }

    protected fun bindResponseState(response: LiveData<Response>) {
        _authState.postValue(mapResponseState(response.value))
        _authState.addSource(response) {
            _authState.postValue(mapResponseState(it))
        }
    }

    private fun mapResponseState(response: Response?): AuthState =
        when (response?.state) {
            Response.ResponseState.SUCCEED -> AuthState(AuthState.Status.AUTHENTICATED)
            Response.ResponseState.FAILED -> AuthState(AuthState.Status.INVALID_AUTHENTICATION, response.message)
            else -> AuthState(AuthState.Status.AUTHENTICATING)
        }

}