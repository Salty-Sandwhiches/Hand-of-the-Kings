package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_end_of_turn.*

import nl.looplan.hotk.R

/**
 * A simple [Fragment] subclass.
 */
class EndOfTurnFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_of_turn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_end_of_turn_next.setOnClickListener {
            val action = EndOfTurnFragmentDirections.actionEndOfTurnFragmentToDecisionPlayerTurnFragment()
            findNavController().navigate(action)
        }
    }

}
