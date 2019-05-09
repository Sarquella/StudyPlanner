package dev.sarquella.studyplanner.ui.sign.signUp

import dev.sarquella.studyplanner.repo.UserRepo
import dev.sarquella.studyplanner.ui.sign.abstractions.SignViewModel


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SignUpViewModel(userRepo: UserRepo) : SignViewModel(userRepo) {

    override fun sign(email: String, password: String) {
        val response = userRepo.signUp(email, password)
        bindResponseState(response)
    }
}