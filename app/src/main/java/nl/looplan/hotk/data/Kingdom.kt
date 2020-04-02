package nl.looplan.hotk.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

data class Kingdom(
    @DocumentId() var id: String,
    @PropertyName("name")var name: String,
    @PropertyName("lands")var lands: ArrayList<Land>) {

    constructor() : this("", "", ArrayList()) {

    }

    override fun equals(other: Any?): Boolean {
        if(other is Kingdom) {
            return (other.name == this.name)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + lands.hashCode()
        return result
    }

    companion object {
        val All: MutableList<Kingdom> = ArrayList()

        suspend fun fetchKingdoms(): Boolean {
            return try {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("kingdoms")
                    .get()
                    .await()

                val kingdoms: MutableList<Kingdom>? = snapshot?.toObjects(Kingdom::class.java)
                if(kingdoms != null) {
                    All.addAll(kingdoms)
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}