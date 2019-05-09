package dev.sarquella.studyplanner.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.sarquella.studyplanner.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class LaunchFragment : Fragment() {

    private val viewModel: LaunchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_launch, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.destination.observe(this, Observer { destination ->
            findNavController().navigate(
                when(destination) {
                    LaunchViewModel.Destination.SIGN -> R.id.action_nav_to_sign_graph
                    LaunchViewModel.Destination.APP -> R.id.action_nav_to_app_graph
                }
            )
        })
    }

}
