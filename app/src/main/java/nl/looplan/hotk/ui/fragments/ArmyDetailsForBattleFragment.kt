package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_army_details_for_battle.*

import nl.looplan.hotk.R
import nl.looplan.hotk.tools.BattleSimulator
import nl.looplan.hotk.ui.AttackViewModel

/**
 * A simple [Fragment] subclass.
 */
class ArmyDetailsForBattleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_army_details_for_battle, container, false)
    }

    private fun updateArmyDetails() {
        val attackViewModel: AttackViewModel by activityViewModels()
        val attackingNumber: Int? = fragment_army_details_for_battle_army_size_left_input.text.toString().toIntOrNull()
        val defendingNumber: Int? = fragment_army_details_for_battle_army_size_right_input.text.toString().toIntOrNull()
        val attackingBonus: Double? = fragment_army_details_for_battle_bonus_left_input.text.toString().toDoubleOrNull()
        val defendingBonus: Double? = fragment_army_details_for_battle_bonus_right_input.text.toString().toDoubleOrNull()
        attackViewModel.apply {
            this.amountOfAttackers.value = attackingNumber ?: 0
            this.amountOfDefenders.value = defendingNumber ?: 0
            this.attackersBonus.value = attackingBonus ?: 0.0
            this.defendersBonus.value = defendingBonus ?: 0.0
        }
    }

    private fun runBattle(): BattleSimulator.Result {
        val attackViewModel: AttackViewModel by activityViewModels()
        val result = BattleSimulator.run(
            attackViewModel.amountOfAttackers.value!!,
            attackViewModel.amountOfDefenders.value!!,
            attackViewModel.attackersBonus.value!!,
            attackViewModel.defendersBonus.value!!
        )
        attackViewModel.battleResult.value = result
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attackViewModel: AttackViewModel by activityViewModels()
        fragment_army_details_for_battle_title.text = "Battle of ${attackViewModel.destinationLand.value?.name}"
        fragment_army_details_for_battle_fight_button.setOnClickListener {
            updateArmyDetails()
            val result = runBattle()

            val action = when {
                result.remainingAttackers <= 1 -> {
                    ArmyDetailsForBattleFragmentDirections.actionArmyDetailsForBattleFragmentToResultsDefeatForBattleFragment()
                }
                result.remainingDefenders == 0 -> {
                    ArmyDetailsForBattleFragmentDirections.actionArmyDetailsForBattleFragmentToResultsVictoryForBattleFragment()
                }
                else -> {
                    ArmyDetailsForBattleFragmentDirections.actionArmyDetailsForBattleFragmentToResultsInconclusiveForBattleFragment()
                }
            }

            findNavController().navigate(action)
        }
    }

}
