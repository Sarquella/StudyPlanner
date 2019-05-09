package dev.sarquella.studyplanner.repo

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import dev.sarquella.studyplanner.EMAIL
import dev.sarquella.studyplanner.PASSWORD
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.data.Response
import dev.sarquella.studyplanner.managers.AuthManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class UserRepoTest {

    private val authManager: AuthManager = mockk(relaxed = true)
    private val userRepo = UserRepo(authManager)

    private val authTask: Task<AuthResult> = mockk()
    private val onCompleteListener = slot<OnCompleteListener<AuthResult>>()

    @Nested
    inner class SignUp {

        init {
            every { authManager.createUserWithEmailAndPassword(any(), any()) } returns authTask
            every { authTask.addOnCompleteListener(capture(onCompleteListener)) } returns authTask
        }

        @Test
        fun `when called then authManager#createUserWithEmailAndPassword is called`() {
            userRepo.signUp(EMAIL, PASSWORD)

            verify { authManager.createUserWithEmailAndPassword(EMAIL, PASSWORD) }
        }

        @Test
        fun `initial response state is progress`() {
            val response = userRepo.signUp(EMAIL, PASSWORD)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.PROGRESS))
        }

        @Test
        fun `if auth succeed then response state is succeed`() {
            every { authTask.isSuccessful } returns true
            val response = userRepo.signUp(EMAIL, PASSWORD)
            onCompleteListener.captured.onComplete(authTask)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.SUCCEED))
        }

        @Test
        fun `if auth failed then response state is failed with error message`() {
            val errorMessage = "Error message"
            every { authTask.isSuccessful } returns false
            every { authTask.exception } returns Exception(errorMessage)
            val response = userRepo.signUp(EMAIL, PASSWORD)
            onCompleteListener.captured.onComplete(authTask)

            assertThat(response.value).isEqualTo(
                Response(
                    Response.ResponseState.FAILED,
                    errorMessage
                )
            )
        }
    }

    @Nested
    inner class SignIn {

        init {
            every { authManager.signInWithEmailAndPassword(any(), any()) } returns authTask
            every { authTask.addOnCompleteListener(capture(onCompleteListener)) } returns authTask
        }

        @Test
        fun `when called then authManager#signInWithEmailAndPassword is called`() {
            userRepo.signIn(EMAIL, PASSWORD)

            verify { authManager.signInWithEmailAndPassword(EMAIL, PASSWORD) }
        }

        @Test
        fun `initial response state is progress`() {
            val response = userRepo.signIn(EMAIL, PASSWORD)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.PROGRESS))
        }

        @Test
        fun `if auth succeed then response state is succeed`() {
            every { authTask.isSuccessful } returns true
            val response = userRepo.signIn(EMAIL, PASSWORD)
            onCompleteListener.captured.onComplete(authTask)

            assertThat(response.value).isEqualTo(Response(Response.ResponseState.SUCCEED))
        }

        @Test
        fun `if auth failed then response state is failed with error message`() {
            val errorMessage = "Error message"
            every { authTask.isSuccessful } returns false
            every { authTask.exception } returns Exception(errorMessage)
            val response = userRepo.signIn(EMAIL, PASSWORD)
            onCompleteListener.captured.onComplete(authTask)

            assertThat(response.value).isEqualTo(
                Response(
                    Response.ResponseState.FAILED,
                    errorMessage
                )
            )
        }
    }

    @Nested
    inner class IsUserSigned {

        private val user: FirebaseUser = mockk()

        @Test
        fun `when called then authManager#currentUser is called`() {
            userRepo.isUserSigned()

            verify { authManager.currentUser }
        }

        @Test
        fun `if user is null then returns false`() {
            every { authManager.currentUser } returns null

            val isUserSigned = userRepo.isUserSigned()

            assertThat(isUserSigned).isFalse()
        }

        @Test
        fun `if user is not null then returns true`() {
            every { authManager.currentUser } returns user

            val isUserSigned = userRepo.isUserSigned()

            assertThat(isUserSigned).isTrue()
        }
    }

}