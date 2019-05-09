package dev.sarquella.studyplanner.ui.sign.signIn

import dev.sarquella.studyplanner.repo.UserRepo
import dev.sarquella.studyplanner.ui.sign.abstractions.SignViewModel


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SignInViewModel(userRepo: UserRepo) : SignViewModel(userRepo) {

    override fun sign(email: String, password: String) {
        val response = userRepo.signIn(email, password)
        bindResponseState(response)
    }
}