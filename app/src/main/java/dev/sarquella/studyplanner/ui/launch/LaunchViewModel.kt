package dev.sarquella.studyplanner.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sarquella.studyplanner.repo.UserRepo


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class LaunchViewModel(userRepo: UserRepo) : ViewModel() {

    enum class Destination {
        SIGN,
        APP
    }

    private val _destination = MutableLiveData<Destination>()
    val destination: LiveData<Destination> = _destination

    init {
        _destination.postValue(
            if (userRepo.isUserSigned())
                Destination.APP
            else
                Destination.SIGN
        )
    }

}