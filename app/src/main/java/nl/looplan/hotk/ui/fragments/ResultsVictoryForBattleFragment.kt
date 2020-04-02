package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_results_victory_for_battle.*

import nl.looplan.hotk.R
import nl.looplan.hotk.ui.AttackViewModel
import nl.looplan.hotk.ui.MainViewModel

/**
 * A simple [Fragment] subclass.
 */
class ResultsVictoryForBattleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results_victory_for_battle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val attackViewModel: AttackViewModel by activityViewModels()
        fragment_results_victory_for_battle_attacker.text = "${attackViewModel.battleResult.value!!.remainingAttackers}"
        fragment_results_victory_for_battle_defender.text = "${attackViewModel.battleResult.value!!.remainingDefenders}"

        fragment_results_victory_for_battle_next.setOnClickListener {
            val mainViewModel: MainViewModel by activityViewModels()
            val land = attackViewModel.destinationLand.value!!

            val attackingPlayer = mainViewModel.currentPlayer.value!!.apply {
                this.lands.add(land)
            }

            val defendingPlayer = attackViewModel.defendingPlayer.value!!.apply {
                this.lands.remove(land)
            }

            // Update attacker
            mainViewModel.getPlayerReference(attackingPlayer.id)
                .set(attackingPlayer)

            // Update defender
            mainViewModel.getPlayerReference(defendingPlayer.id)
                .set(defendingPlayer)

            val action = ResultsVictoryForBattleFragmentDirections.actionResultsVictoryForBattleFragmentToDecisionAfterAttackFragment()
            findNavController().navigate(action)
        }
    }

}
