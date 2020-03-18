package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_money_earned_by_saving_up.*

import nl.looplan.hotk.R
import nl.looplan.hotk.data.Kingdom
import nl.looplan.hotk.data.Player
import nl.looplan.hotk.ui.AttackViewModel
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

        updateMoneyEarned()
    }

    private fun updateMoneyEarned() {
        val income = calculateMoneyEarned()
        fragment_money_earned_by_saving_up_title.text = "$income"
    }

    private fun calculateMoneyEarned(): Number {
        val mainViewModel: MainViewModel by activityViewModels()
        val player = mainViewModel.player.value!!
        val multiplier = if(player.hasKingdom) {
            15
        } else {
            20
        }
        return player.lands.size * multiplier
    }
}
