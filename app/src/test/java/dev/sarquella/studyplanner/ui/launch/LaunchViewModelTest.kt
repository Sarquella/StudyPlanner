package dev.sarquella.studyplanner.ui.launch

import dev.sarquella.studyplanner.junit.extensions.InstantTaskExecutorExtension
import dev.sarquella.studyplanner.repo.UserRepo
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

@ExtendWith(InstantTaskExecutorExtension::class)
class LaunchViewModelTest {

    private val userRepo: UserRepo = mockk()
    private lateinit var viewModel: LaunchViewModel

    @Nested
    inner class Destination {

        @Test
        fun `when user is not signed in then destination is SIGN`() {
            every { userRepo.isUserSigned() } returns false

            viewModel = LaunchViewModel(userRepo)

            assertThat(viewModel.destination.value).isEqualTo(LaunchViewModel.Destination.SIGN)
        }

        @Test
        fun `when user is signed in then destination is APP`() {
            every { userRepo.isUserSigned() } returns true

            viewModel = LaunchViewModel(userRepo)

            assertThat(viewModel.destination.value).isEqualTo(LaunchViewModel.Destination.APP)
        }

    }

}