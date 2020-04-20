package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_land_selection_for_battle.*

import nl.looplan.hotk.R
import nl.looplan.hotk.data.Land
import nl.looplan.hotk.ui.AttackViewModel
import nl.looplan.hotk.ui.MainViewModel

/**
 * A simple [Fragment] subclass.
 */
class LandSelectionForBattleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_land_selection_for_battle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val landNames = Land.All.map {
                land -> land.name
        }.toTypedArray()
        val adapter = ArrayAdapter(this.context!!, android.R.layout.simple_dropdown_item_1line, landNames)
        fragment_land_selection_for_battle_destination.setAdapter(adapter)
        fragment_land_selection_for_battle_destination.threshold = 1
        adapter.notifyDataSetChanged()



        fragment_land_selection_for_battle_next_button.setOnClickListener {
            val landName = fragment_land_selection_for_battle_destination.text.toString()
            if(landName.isNotBlank()) {
                val land = Land.All.singleOrNull { land ->
                    (land.name == landName)
                }

                if(land != null) {
                    val mainViewModel: MainViewModel by activityViewModels()
                    val attackViewModel: AttackViewModel by activityViewModels()

                    attackViewModel.destinationLand.value = land
                    attackViewModel.defendingPlayer.value = mainViewModel.players.value?.singleOrNull {
                            player -> player.lands.contains(land)
                    }

                    val action = LandSelectionForBattleFragmentDirections.actionLandSelectionForBattleFragmentToArmyDetailsForBattleFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

}
