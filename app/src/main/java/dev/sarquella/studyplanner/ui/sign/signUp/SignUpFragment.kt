package dev.sarquella.studyplanner.ui.sign.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.databinding.FragmentSignBinding
import dev.sarquella.studyplanner.ui.sign.abstractions.SignViewModel
import kotlinx.android.synthetic.main.fragment_sign.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSignBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStaticUI()
        bindObservables()
    }

    private fun setStaticUI() {
        tvTitle.text = getString(R.string.Sign_Up)
        etPassword.inputType = EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        btSign.text = getString(R.string.Sign_Up)
        tvNavToNext.text = getString(R.string.already_have_an_account)
        btNavToNext.text = getString(R.string.Sign_In)
    }

    private fun bindObservables() {
        bindNavToNext()
        bindAuthState()
    }

    private fun bindNavToNext() {
        viewModel.navToNext.observe(this, Observer { navToNext ->
            if (navToNext)
                findNavController().navigate(R.id.action_nav_to_sign_in)
        })
    }

    private fun bindAuthState() {
        viewModel.authState.observe(this, Observer {

            if (it.status == SignViewModel.AuthState.Status.AUTHENTICATED)
                findNavController().navigate(R.id.action_nav_to_app_graph)

            buttonsContainer.visibility =
                if (it.status == SignViewModel.AuthState.Status.AUTHENTICATING)
                    View.INVISIBLE
                else
                    View.VISIBLE

            progressBar.visibility =
                if (it.status != SignViewModel.AuthState.Status.AUTHENTICATING)
                    View.INVISIBLE
                else
                    View.VISIBLE

            errorCard.visibility =
                if (it.status != SignViewModel.AuthState.Status.INVALID_AUTHENTICATION)
                    View.INVISIBLE
                else
                    View.VISIBLE

            tvError.text = it.message ?: getString(R.string.an_error_has_occurred)
        })
    }

}