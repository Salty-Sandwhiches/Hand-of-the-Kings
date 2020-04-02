package nl.looplan.hotk.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Player(
    @DocumentId() var id: String,
    @PropertyName("name")var name: String,
    @PropertyName("lands")var lands: MutableList<Land>) {

    constructor() : this("","", ArrayList()) {

    }

    val hasKingdom: Boolean
    get() {
        for(kingdom in Kingdom.All) {
            val hasKingdom = lands.containsAll(kingdom.lands)
            if(hasKingdom) {
                return true
            }
        }
        return false
    }

    val income: Int
    get() {
        var income = 0
        lands.forEach { land ->
            income += land.income
        }
        return income
    }

    val saveIncome: Int
    get() {
        val multiplier = if(hasKingdom) {
            15
        } else {
            20
        }
        return lands.size * multiplier
    }
}