package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_decision_after_attack.*

import nl.looplan.hotk.R
import nl.looplan.hotk.ui.MainViewModel

/**
 * A simple [Fragment] subclass.
 */
class DecisionAfterAttackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decision_after_attack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_decision_after_attack_attack.setOnClickListener {
            val action = DecisionAfterAttackFragmentDirections.actionDecisionAfterAttackFragmentToLandSelectionForBattleFragment()
            findNavController().navigate(action)
        }
        fragment_decision_after_attack_done.setOnClickListener {
            val mainViewModel: MainViewModel by activityViewModels()
            val hasEverybodyTakenItsTurn = mainViewModel.hasEveryPlayerTakenItsTurn()
            val action = if(hasEverybodyTakenItsTurn) {
                mainViewModel.nextTurn()
                DecisionAfterAttackFragmentDirections.actionDecisionAfterAttackFragmentToEndOfTurnFragment()
            } else {
                mainViewModel.nextPlayer()
                DecisionAfterAttackFragmentDirections.actionDecisionAfterAttackFragmentToDecisionPlayerTurnFragment()
            }
            findNavController().navigate(action)
        }
    }

}
