package dev.sarquella.studyplanner.ui.app.profile

import dev.sarquella.studyplanner.data.entities.User
import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.UserRepo
import io.mockk.every
import io.mockk.mockk
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
class ProfileViewModelTest {

    private val userRepo: UserRepo = mockk(relaxUnitFun = true)
    private val viewModel: ProfileViewModel

    private val user: dev.sarquella.studyplanner.data.entities.User = mockk()

    init {
        every { userRepo.getCurrentUser() } returns user
        viewModel = ProfileViewModel(userRepo)
    }

    @Nested
    inner class User {

        @Test
        fun `check viewModel#user matches to userRepo#getCurrentUser`() {
            assertThat(viewModel.user).isEqualTo(user)
        }

    }

    @Nested
    inner class ReturnToSign {

        @Test
        fun `check returnToSign is not true initially`() {
            assertThat(viewModel.returnToSign.value).isIn(null, false)
        }

    }

    @Nested
    inner class SignOut {

        @Test
        fun `when called then userRepo#signOut is called and returnToSign is true`() {
            viewModel.signOut()

            verify { userRepo.signOut() }
            assertThat(viewModel.returnToSign.value).isTrue()
        }

    }
}