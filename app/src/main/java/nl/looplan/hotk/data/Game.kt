package nl.looplan.hotk.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Game(
    @DocumentId() var id: String,
    @PropertyName("name") var currentTurn: Int,
    @PropertyName("createdAt") var createdAt: Timestamp) {

    constructor() : this("", 0, Timestamp.now()) {

    }

}