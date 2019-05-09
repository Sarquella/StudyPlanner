package dev.sarquella.studyplanner.ui.app


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import dev.sarquella.studyplanner.R
import kotlinx.android.synthetic.main.fragment_app.*

/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

class AppFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_app, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            bottomNavMenu.setupWithNavController(Navigation.findNavController(it, R.id.bottomNavFragment))
        }
    }
}
