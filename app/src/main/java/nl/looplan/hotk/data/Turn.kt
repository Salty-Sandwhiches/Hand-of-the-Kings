package nl.looplan.hotk.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Turn(
    @DocumentId() var id: String,
    var turnStarted: Timestamp,
    var currentPlayerId: String,
    var playerOrder: MutableList<String>) {

    constructor() : this("", Timestamp.now(), "", mutableListOf()) {

    }
}