package nl.looplan.hotk.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Player(
    @DocumentId() var id: String,
    @PropertyName("name")var name: String,
    @PropertyName("lands")var lands: ArrayList<String>) {

    constructor() : this("","", ArrayList()) {

    }

    val hasKingdom: Boolean
    get() {
        for(kingdom in Kingdom.All) {
            val landIds = kingdom.lands.map { land -> land.id }
            val hasKingdom = lands.containsAll(landIds)
            if(hasKingdom) {
                return true
            }
        }
        return false
    }

    val income: Int
    get() {
        var income = 0
        val lands = Land.All.filter { land ->
            this.lands.contains(land.id)
        }
        lands.forEach { land ->
            income += land.income
        }
        return income
    }
}