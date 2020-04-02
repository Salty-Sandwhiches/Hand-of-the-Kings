package nl.looplan.hotk.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

data class Land(
    @DocumentId() var id: String,
    @PropertyName("Name") var name: String,
    @PropertyName("Kingdom")var kingdom: String,
    @PropertyName("Income")var income: Int) {

    constructor(): this("", "", "", 0) {

    }

    override fun equals(other: Any?): Boolean {
        if(other is Land) {
            return (other.name == this.name)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + kingdom.hashCode()
        result = 31 * result + income.hashCode()
        return result
    }

    companion object {
        val All: MutableList<Land> = ArrayList()

        suspend fun fetchLands(): Boolean {
            return try {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("lands")
                    .get()
                    .await()

                val lands: MutableList<Land>? = snapshot?.toObjects(
                    Land::class.java)
                if(lands != null) {
                    All.addAll(lands)
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        }
    }
}
