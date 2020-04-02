package nl.looplan.hotk.data

import com.google.firebase.Timestamp

data class Event(var type: String, var timestamp: Timestamp = Timestamp.now()) {
    companion object {
        fun newAttackEvent(): Event {
            return Event("ATTACK")
        }
        fun newSavingUpEvent(): Event {
            return Event("SAVING_UP")
        }
    }
}