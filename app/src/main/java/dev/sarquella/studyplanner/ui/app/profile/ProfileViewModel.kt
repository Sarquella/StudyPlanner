package dev.sarquella.studyplanner.ui.app.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.repo.UserRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ProfileViewModel(private val userRepo: UserRepo) : ViewModel() {

    val user = userRepo.getCurrentUser()

    private val _returnToSign = MutableLiveData<Boolean>()
    val returnToSign: LiveData<Boolean> = _returnToSign

    fun signOut() {
        userRepo.signOut()
        _returnToSign.postValue(true)
    }

}