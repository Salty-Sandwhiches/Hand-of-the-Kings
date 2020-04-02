package nl.looplan.hotk.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import nl.looplan.hotk.data.Land
import nl.looplan.hotk.data.Player
import nl.looplan.hotk.tools.BattleSimulator

class AttackViewModel : ViewModel() {
    val defendingPlayer: MutableLiveData<Player> = MutableLiveData()
    val destinationLand: MutableLiveData<Land> = MutableLiveData()
    val amountOfAttackers: MutableLiveData<Int> = MutableLiveData()
    val amountOfDefenders: MutableLiveData<Int> = MutableLiveData()
    val attackersBonus: MutableLiveData<Double> = MutableLiveData()
    val defendersBonus: MutableLiveData<Double> = MutableLiveData()
    val battleResult: MutableLiveData<BattleSimulator.Result> = MutableLiveData()

}