package nl.looplan.hotk.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import nl.looplan.hotk.data.Player
import nl.looplan.hotk.data.Turn

class MainViewModel : ViewModel() {

    val turn: MutableLiveData<Turn> = MutableLiveData()

    val currentPlayer: MutableLiveData<Player> = MutableLiveData()

    val players: MutableLiveData<List<Player>> = MutableLiveData()

    init {
        players.observeForever(Observer { players ->
            val updatedCurrentPlayer = players.singleOrNull { player ->
                player.id == currentPlayer.value?.id
            }
            currentPlayer.value = updatedCurrentPlayer
        })
    }

    fun setup(id: String) {
        FirebaseFirestore.getInstance()
            .collection("players")
            .addSnapshotListener { documentSnapshot, _ ->
                players.value = documentSnapshot?.toObjects(Player::class.java)
            }
    }

    fun endTurn() {
        players.value?.forEach { player ->
            turn.value?.endingIncomes?.set(player.id, player.income)
        }
        FirebaseFirestore.getInstance()
            .collection("turns")
            .document()
            .set(turn.value!!)
    }
}