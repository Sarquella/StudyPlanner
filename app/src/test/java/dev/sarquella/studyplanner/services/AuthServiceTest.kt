package dev.sarquella.studyplanner.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.sarquella.studyplanner.EMAIL
import dev.sarquella.studyplanner.PASSWORD
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AuthServiceTest {

    private val firebaseAuth: FirebaseAuth = mockk(relaxed = true)
    private val authService = AuthService(firebaseAuth)

    @Nested
    inner class CurrentUser {

        @Test
        fun `check authService current user matches firebaseAuth current user`() {
            val currentUser: FirebaseUser? = mockk()
            every { firebaseAuth.currentUser } returns currentUser

            assertThat(authService.currentUser).isEqualTo(currentUser)
        }

    }

    @Nested
    inner class CreateUserWithEmailAndPassword {

        @Test
        fun `check authService call matches firebaseAuth call`() {
            authService.createUserWithEmailAndPassword(EMAIL, PASSWORD)

            verify { firebaseAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD) }
        }

    }

    @Nested
    inner class SignInWithEmailAndPassword {

        @Test
        fun `check authService call matches firebaseAuth call`() {
            authService.signInWithEmailAndPassword(EMAIL, PASSWORD)

            verify { firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD) }
        }

    }

    @Nested
    inner class SignOut {

        @Test
        fun `check authService call matches firebaseAuth call`() {
            authService.signOut()

            verify { firebaseAuth.signOut() }
        }

    }
}