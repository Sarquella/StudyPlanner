package dev.sarquella.studyplanner.repo

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import dev.sarquella.studyplanner.EMAIL
import dev.sarquella.studyplanner.PASSWORD
import dev.sarquella.studyplanner.data.entities.User
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.data.vo.Response
import dev.sarquella.studyplanner.services.AuthService
import dev.sarquella.studyplanner.services.ApiService
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

    private val authService: AuthService = mockk(relaxed = true)
    private val apiService: ApiService = mockk(relaxed = true)
    private val userRepo = UserRepo(authService, apiService)

    private val authTask: Task<AuthResult> = mockk()
    private val onCompleteListener = slot<OnCompleteListener<AuthResult>>()

    @Nested
    inner class Collection {

        @Test
        fun `check collection has correct name`() {
            assertThat(UserRepo.COLLECTION).isEqualTo("users")
        }

    }

    @Nested
    inner class SignUp {

        init {
            every { authService.createUserWithEmailAndPassword(any(), any()) } returns authTask
            every { authTask.addOnCompleteListener(capture(onCompleteListener)) } returns authTask
        }

        @Test
        fun `when called then authService#createUserWithEmailAndPassword is called`() {
            userRepo.signUp(EMAIL, PASSWORD)

            verify { authService.createUserWithEmailAndPassword(EMAIL, PASSWORD) }
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
            every { authService.signInWithEmailAndPassword(any(), any()) } returns authTask
            every { authTask.addOnCompleteListener(capture(onCompleteListener)) } returns authTask
        }

        @Test
        fun `when called then authService#signInWithEmailAndPassword is called`() {
            userRepo.signIn(EMAIL, PASSWORD)

            verify { authService.signInWithEmailAndPassword(EMAIL, PASSWORD) }
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
    inner class SignOut {

        @Test
        fun `when called then authService#signOut is called`() {
            userRepo.signOut()

            verify { authService.signOut() }
        }

    }

    @Nested
    inner class IsUserSigned {

        private val user: FirebaseUser = mockk()

        @Test
        fun `when called then authService#currentUser is called`() {
            userRepo.isUserSigned()

            verify { authService.currentUser }
        }

        @Test
        fun `if user is null then returns false`() {
            every { authService.currentUser } returns null

            val isUserSigned = userRepo.isUserSigned()

            assertThat(isUserSigned).isFalse()
        }

        @Test
        fun `if user is not null then returns true`() {
            every { authService.currentUser } returns user

            val isUserSigned = userRepo.isUserSigned()

            assertThat(isUserSigned).isTrue()
        }
    }

    @Nested
    inner class GetCurrentUser {

        private val user: FirebaseUser = mockk(relaxed = true)

        @Test
        fun `if user is null then returns null`() {
            every { authService.currentUser } returns null

            val currentUser = userRepo.getCurrentUser()

            assertThat(currentUser).isNull()
        }

        @Test
        fun `if user is not null then returns corresponding user`() {
            every { authService.currentUser } returns user

            val currentUser = userRepo.getCurrentUser()

            assertThat(currentUser).isEqualTo(User.fromFirebaseUser(user))
        }

    }

    @Nested
    inner class GetCurrentUserReference {

        @Test
        fun `when called asks apiService for collection document with authService#currentUser#uid`() {
            val userId = "userId"
            every { authService.currentUser?.uid } returns userId

            userRepo.getCurrentUserReference()

            verify { apiService.collection(UserRepo.COLLECTION).document(userId) }
        }

        @Test
        fun `returns matching apiService collection document`() {
            val userRef: DocumentReference = mockk()
            every { authService.currentUser?.uid } returns "userId"
            every { apiService.collection(any()).document(any()) } returns userRef

            val expected = userRepo.getCurrentUserReference()

            assertThat(expected).isEqualTo(userRef)
        }

    }

}