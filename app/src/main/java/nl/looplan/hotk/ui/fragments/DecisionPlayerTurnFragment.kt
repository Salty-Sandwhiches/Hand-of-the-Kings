package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_decision_player_turn.*

import nl.looplan.hotk.R
import nl.looplan.hotk.data.Player
import nl.looplan.hotk.ui.MainViewModel

/**
 * A simple [Fragment] subclass.
 */
class DecisionPlayerTurnFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decision_player_turn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel: MainViewModel by activityViewModels()

        mainViewModel.currentPlayer.observe(viewLifecycleOwner, Observer { player: Player? ->
            fragment_decision_player_turn_name.text = player?.name
            fragment_decision_player_turn_stats_income.text = "Income: ${player?.income}"
            fragment_decision_player_turn_stats_save.text = "Save: ${player?.saveIncome}"
            fragment_decision_player_turn_stats_lands.text = "Lands: ${player?.lands?.size}"
        })

        fragment_decision_player_turn_battle.setOnClickListener {
            val action = DecisionPlayerTurnFragmentDirections.actionDecisionPlayerTurnFragmentToLandSelectionForBattleFragment()
            findNavController().navigate(action)
        }

        fragment_decision_player_turn_save_up.setOnClickListener {
            val action = DecisionPlayerTurnFragmentDirections.actionDecisionPlayerTurnFragmentToMoneyEarnedBySavingUpFragment()
            findNavController().navigate(action)
        }
    }

}
