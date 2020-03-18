package nl.looplan.hotk.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Turn(
    @DocumentId() var id: String,
    @PropertyName("endingIncomes") var endingIncomes: List<Income>) {
}

data class Income(var id: String, var income: Int) {

}