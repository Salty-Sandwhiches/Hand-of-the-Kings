package nl.looplan.hotk.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import nl.looplan.hotk.data.*

class MainViewModel : ViewModel() {

    val game: MutableLiveData<Game> = MutableLiveData()

    val turn: MutableLiveData<Turn> = MutableLiveData()

    val players: MutableLiveData<List<Player>> = MutableLiveData()

    val currentPlayer: MutableLiveData<Player> = MutableLiveData()

    private var gameListenerRegistration: ListenerRegistration? = null

    private var turnListenerRegistration: ListenerRegistration? = null

    private var playersListenerRegistration: ListenerRegistration? = null

    init {
        game.observeForever { game ->
            setPlayerListener(game.id, game.currentTurn.toString())
            setTurnListener(game.id, game.currentTurn.toString())
        }

        turn.observeForever { turn ->
            currentPlayer.value = players.value?.singleOrNull { player ->
                player.id == turn.currentPlayerId
            }
        }

        players.observeForever { players ->
            val updatedCurrentPlayer = players.singleOrNull { player ->
                player.id == currentPlayer.value?.id
            }
            currentPlayer.value = updatedCurrentPlayer
        }
    }

    fun getGameReference(): DocumentReference {
        return FirebaseFirestore.getInstance().collection("games").document(game.value?.id ?: "")
    }

    fun getTurnReference(turnId: String): DocumentReference {
        return getGameReference().collection("turns").document(turnId)
    }

    fun getCurrentTurnReference(): DocumentReference {
        return getTurnReference(turn.value?.id ?: "")
    }

    fun getPlayerReference(playerId: String): DocumentReference {
        return getCurrentTurnReference().collection("players").document(playerId)
    }

    fun createGame() {
        val players = listOf(
            Player("", "Rood", Land.All.filter { land ->
                mutableListOf<String>("Strong Song", "Blackhaven", "Goldengrove", "Goldentooth", "Bronzegate", "The Twins", "Dreadfort", "The Whispers").contains(land.name)
            }.toMutableList()),
            Player("", "Blauw", Land.All.filter { land ->
                mutableListOf<String>("Maidenpool", "Kingswood", "Fairmarket", "Silverhill", "Kingsgrave", "Saltpans", "Pinkmaiden", "The Crag").contains(land.name)
            }.toMutableList()),
            Player("", "Zwart", Land.All.filter { land ->
                mutableListOf<String>("Bear Island", "The Rills", "White Harbor", "The Gift", "Mountains of the Moon", "Yronwood", "Tarth", "Sharp Point").contains(land.name)
            }.toMutableList()),
            Player("", "Geel", Land.All.filter { land ->
                mutableListOf<String>("Wolfswood", "Karhold", "Saltshore", "Coldwater", "The Bloody Gate", "Bitterbridge", "Summerhall", "Harrenhall", "Cape Kraken").contains(land.name)
            }.toMutableList()),
            Player("", "Groen", Land.All.filter { land ->
                mutableListOf<String>("The Neck", "Mistwood", "Sandstone", "Crakehall", "The Arbor", "Ashford", "Old Town", "Grassy Vale").contains(land.name)
            }.toMutableList())
        )

        // Create game and set turn to 0
        val gameRef = FirebaseFirestore.getInstance()
            .collection("games")
            .document()
        gameRef.set(Game("", 0, Timestamp.now()))

        // Create a turn as base turn for the bank
        val baseTurnRef = gameRef.collection("turns")
            .document("-1")
        for(player in players) {
            val playerRef = baseTurnRef.collection("players").document()
            player.id = playerRef.id
            playerRef.set(player)
        }
        baseTurnRef.set(Turn("", Timestamp.now(), players[0].id,
            players.map { it.id } as MutableList<String>))
        
        // Create a turn and set the timestamp
        val turnRef = gameRef.collection("turns")
            .document("0")

        for(player in players) {
            val playerRef = turnRef.collection("players").document()
            player.id = playerRef.id
            playerRef.set(player)
        }
        turnRef.set(Turn("", Timestamp.now(), players[0].id,
            players.map { it.id } as MutableList<String>))

        //turnRef.collection("events").document().set(Event.newAttackEvent())

        setGameListener(gameRef.id)
    }

    private fun setGameListener(gameId: String) {
        this.gameListenerRegistration?.remove()
        this.gameListenerRegistration = FirebaseFirestore.getInstance()
            .collection("games")
            .document(gameId)
            .addSnapshotListener { snapshot, _ ->
                this.game.value = snapshot?.toObject(Game::class.java)
            }
    }

    private fun setTurnListener(gameId: String, turnId: String) {
        this.turnListenerRegistration?.remove()
        this.turnListenerRegistration = FirebaseFirestore.getInstance()
            .collection("games")
            .document(gameId)
            .collection("turns")
            .document(turnId)
            .addSnapshotListener { snapshot, _ ->
                this.turn.value = snapshot?.toObject(Turn::class.java)
            }
    }

    private fun setPlayerListener(gameId: String, turnId: String) {
        this.playersListenerRegistration?.remove()
        this.playersListenerRegistration = FirebaseFirestore.getInstance()
            .collection("games")
            .document(gameId)
            .collection("turns")
            .document(turnId)
            .collection("players")
            .addSnapshotListener { documentSnapshot, _ ->
                players.value = documentSnapshot?.toObjects(Player::class.java)
            }
    }

    fun nextPlayer() {
        val turn = this.turn.value!!
        val currentIndex = turn.playerOrder.indexOf(turn.currentPlayerId)

        val newPlayer = turn.playerOrder[currentIndex + 1]
        getCurrentTurnReference().update("currentPlayerId" , newPlayer)
    }

    fun hasEveryPlayerTakenItsTurn(): Boolean {
        val turn = this.turn.value!!
        val currentIndex = turn.playerOrder.indexOf(turn.currentPlayerId)
        return (currentIndex == turn.playerOrder.lastIndex)
    }

    fun nextTurn() {
        val currentTurn = this.turn.value!!
        val players = this.players.value!!
        val playerOrder = currentTurn.playerOrder.apply {
            val first = first()
            this.remove(first)
            this.add(first)
        }
        val newTurn = this.game.value!!.currentTurn + 1
        val newTurnId = newTurn.toString()

        val turnRef = getTurnReference(newTurnId)

        for(player in players) {
            val playerRef = turnRef.collection("players").document(player.id)
            playerRef.set(player)
        }

        turnRef.set(Turn("", Timestamp.now(), playerOrder[0], playerOrder))

        // Update current turn
        getGameReference().update("currentTurn", newTurn)
    }

}