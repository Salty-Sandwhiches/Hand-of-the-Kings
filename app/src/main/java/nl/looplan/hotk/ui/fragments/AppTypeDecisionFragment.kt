package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_app_type_decision.*

import nl.looplan.hotk.R

/**
 * A simple [Fragment] subclass.
 */
class AppTypeDecisionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_type_decision, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_app_type_decision_game.setOnClickListener {
            val action = AppTypeDecisionFragmentDirections.actionAppTypeDecisionFragmentToDecisionPlayerTurnFragment()
            findNavController().navigate(action)
        }

        fragment_app_type_decision_bank.setOnClickListener {
            val action = AppTypeDecisionFragmentDirections.actionAppTypeDecisionFragmentToBankFragment()
            findNavController().navigate(action)
        }
    }

}
