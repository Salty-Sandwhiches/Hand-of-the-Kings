package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_money_earned_by_saving_up.*

import nl.looplan.hotk.R
import nl.looplan.hotk.data.Player
import nl.looplan.hotk.ui.MainViewModel

/**
 * A simple [Fragment] subclass.
 */
class MoneyEarnedBySavingUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money_earned_by_saving_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel: MainViewModel by activityViewModels()
        mainViewModel.currentPlayer.observe(viewLifecycleOwner, Observer { player ->
            val income = player.saveIncome
            fragment_money_earned_by_saving_up_title.text = "$income"
        })

        fragment_money_earned_by_saving_up_next.setOnClickListener {
            val hasEverybodyTakenItsTurn = mainViewModel.hasEveryPlayerTakenItsTurn()
            val action = if(hasEverybodyTakenItsTurn) {
                mainViewModel.nextTurn()
                MoneyEarnedBySavingUpFragmentDirections.actionMoneyEarnedBySavingUpFragmentToEndOfTurnFragment()
            } else {
                mainViewModel.nextPlayer()
                MoneyEarnedBySavingUpFragmentDirections.actionMoneyEarnedBySavingUpFragmentToDecisionPlayerTurnFragment()
            }
            findNavController().navigate(action)
        }
    }
}
