package dev.sarquella.studyplanner.ui.sign

import androidx.lifecycle.MutableLiveData
import dev.sarquella.studyplanner.EMAIL
import dev.sarquella.studyplanner.PASSWORD
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.helpers.extensions.addObserver
import dev.sarquella.studyplanner.repo.UserRepo
import dev.sarquella.studyplanner.ui.sign.abstractions.SignViewModel
import dev.sarquella.studyplanner.ui.sign.signIn.SignInViewModel
import dev.sarquella.studyplanner.data.Response
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class SignInViewModelTest {

    private val userRepo: UserRepo = mockk()
    private val viewModel: SignInViewModel = SignInViewModel(userRepo)

    @Nested
    inner class NavToNext {

        @Test
        fun `calling navToNext changes navToNext to true`() {
            viewModel.navToNext()

            assertThat(viewModel.navToNext.value).isTrue()
        }

    }

    @Nested
    inner class SignIn {

        private val response = MutableLiveData<Response>()


        init {
            every { userRepo.signIn(any(), any()) } returns response
        }

        @BeforeEach
        fun setUp() {
            response.postValue(Response(Response.ResponseState.PROGRESS))
            viewModel.authState.addObserver()
        }

        @Test
        fun `if userRepo returns progress state then authState is AUTHENTICATING`() {
            viewModel.sign(EMAIL, PASSWORD)

            assertThat(viewModel.authState.value)
                .isEqualTo(SignViewModel.AuthState(SignViewModel.AuthState.Status.AUTHENTICATING))
        }

        @Test
        fun `if userRepo returns succeed state then authState is AUTHENTICATED`() {
            viewModel.sign(EMAIL, PASSWORD)
            response.postValue(Response(Response.ResponseState.SUCCEED))

            assertThat(viewModel.authState.value)
                .isEqualTo(SignViewModel.AuthState(SignViewModel.AuthState.Status.AUTHENTICATED))

        }

        @Test
        fun `if userRepo returns failed state with error then authState is INVALID_AUTHTENTICATION with error`() {
            val errorMessage = "Error message"

            viewModel.sign(EMAIL, PASSWORD)
            response.postValue(
                Response(
                    Response.ResponseState.FAILED,
                    errorMessage
                )
            )

            assertThat(viewModel.authState.value)
                .isEqualTo(SignViewModel.AuthState(SignViewModel.AuthState.Status.INVALID_AUTHENTICATION, errorMessage))

        }

    }

    @Nested
    inner class IsButtonEnabled {

        @BeforeEach
        fun setUp() {
            viewModel.isButtonEnabled.addObserver()
        }

        @Test
        fun `initial state is not true`() {
            assertThat(viewModel.isButtonEnabled).isNotEqualTo(true)
        }

        @Test
        fun `if empty email and empty password then button is disabled`() {
            viewModel.onEmailChanged("")
            viewModel.onPasswordChanged("")

            assertThat(viewModel.isButtonEnabled.value).isFalse()
        }

        @Test
        fun `if filled email and empty password then button is disabled`() {
            viewModel.onEmailChanged(EMAIL)
            viewModel.onPasswordChanged("")

            assertThat(viewModel.isButtonEnabled.value).isFalse()
        }

        @Test
        fun `if empty email and filled password then button is disabled`() {
            viewModel.onEmailChanged("")
            viewModel.onPasswordChanged(PASSWORD)

            assertThat(viewModel.isButtonEnabled.value).isFalse()
        }

        @Test
        fun `if filled email and filled password then button is enabled`() {
            viewModel.onEmailChanged(EMAIL)
            viewModel.onPasswordChanged(PASSWORD)

            assertThat(viewModel.isButtonEnabled.value).isTrue()
        }

        @Test
        fun `if button enabled and email is cleared then button is disabled`() {
            viewModel.onEmailChanged(EMAIL)
            viewModel.onPasswordChanged(PASSWORD)

            viewModel.onEmailChanged("")
            assertThat(viewModel.isButtonEnabled.value).isFalse()
        }

        @Test
        fun `if button enabled and password is cleared then button is disabled`() {
            viewModel.onEmailChanged(EMAIL)
            viewModel.onPasswordChanged(PASSWORD)

            viewModel.onPasswordChanged("")
            assertThat(viewModel.isButtonEnabled.value).isFalse()
        }

    }

}