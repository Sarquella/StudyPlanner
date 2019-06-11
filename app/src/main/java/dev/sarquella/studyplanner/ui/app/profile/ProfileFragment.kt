package dev.sarquella.studyplanner.ui.app.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.sarquella.studyplanner.R
import dev.sarquella.studyplanner.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindReturnToSign()
    }

    private fun bindReturnToSign() {

        viewModel.returnToSign.observe(this, Observer { returnToSign ->
            if (returnToSign)
                activity?.supportFragmentManager?.fragments?.last()
                    ?.findNavController()?.navigate(R.id.action_nav_to_sign_graph)
        })
    }
}