package dev.sarquella.studyplanner.managers

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

class AuthManagerTest {

    private val firebaseAuth: FirebaseAuth = mockk(relaxed = true)
    private val authManager = AuthManager(firebaseAuth)

    @Nested
    inner class CurrentUser {

        @Test
        fun `check authManager current user matches firebaseAuth current user`() {
            val currentUser: FirebaseUser? = mockk()
            every { firebaseAuth.currentUser } returns currentUser

            assertThat(authManager.currentUser).isEqualTo(currentUser)
        }

    }

    @Nested
    inner class CreateUserWithEmailAndPassword {

        @Test
        fun `check authManager call matches firebaseAuth call`() {
            authManager.createUserWithEmailAndPassword(EMAIL, PASSWORD)

            verify { firebaseAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD) }
        }

    }

    @Nested
    inner class SignInWithEmailAndPassword {

        @Test
        fun `check authManager call matches firebaseAuth call`() {
            authManager.signInWithEmailAndPassword(EMAIL, PASSWORD)

            verify { firebaseAuth.signInWithEmailAndPassword(EMAIL, PASSWORD) }
        }

    }

    @Nested
    inner class SignOut {

        @Test
        fun `check authManager call matches firebaseAuth call`() {
            authManager.signOut()

            verify { firebaseAuth.signOut() }
        }

    }
}