package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_results_inconclusive_for_battle.*

import nl.looplan.hotk.R
import nl.looplan.hotk.tools.BattleSimulator
import nl.looplan.hotk.ui.AttackViewModel

/**
 * A simple [Fragment] subclass.
 */
class ResultsInconclusiveForBattleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results_inconclusive_for_battle, container, false)
    }

    fun runBattle(): BattleSimulator.Result {
        val attackViewModel: AttackViewModel by activityViewModels()
        val result = BattleSimulator.run(
            attackViewModel.battleResult.value!!.remainingAttackers,
            attackViewModel.battleResult.value!!.remainingDefenders,
            attackViewModel.attackersBonus.value!!,
            attackViewModel.defendersBonus.value!!
        )
        attackViewModel.battleResult.value = result
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val attackViewModel: AttackViewModel by activityViewModels()
        fragment_results_inconclusive_for_battle_army_A_left.text = "${attackViewModel.battleResult.value!!.remainingAttackers}"
        fragment_results_inconclusive_for_battle_army_B_left.text = "${attackViewModel.battleResult.value!!.remainingDefenders}"

        fragment_results_inconclusive_for_battle_again.setOnClickListener {
            val result = runBattle()
            val action = when {
                result.remainingAttackers <= 1 -> {
                    ResultsInconclusiveForBattleFragmentDirections.actionResultsInconclusiveForBattleFragmentToResultsDefeatForBattleFragment()
                }
                result.remainingDefenders == 0 -> {
                    ResultsInconclusiveForBattleFragmentDirections.actionResultsInconclusiveForBattleFragmentToResultsVictoryForBattleFragment()
                }
                else -> {
                    ResultsInconclusiveForBattleFragmentDirections.actionResultsInconclusiveForBattleFragmentSelf()
                }
            }
            findNavController().navigate(action)
        }

        fragment_results_inconclusive_for_battle_stop.setOnClickListener {
            val action = ResultsInconclusiveForBattleFragmentDirections.actionResultsInconclusiveForBattleFragmentToDecisionAfterAttackFragment()
            findNavController().navigate(action)
        }
    }

}
