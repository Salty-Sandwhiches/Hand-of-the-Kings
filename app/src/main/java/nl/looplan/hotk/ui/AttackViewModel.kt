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

    init {
        destinationLand.observeForever { land ->
            FirebaseFirestore.getInstance()
                .collection("players")
                .whereArrayContains("lands", land.id)
                .get()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        val result = task.result!!
                        val playerDocument = result.documents.singleOrNull()
                        val player = playerDocument?.toObject(Player::class.java)
                        if(player != null) {
                            defendingPlayer.value = player
                        }
                    }
                }
        }
    }
}