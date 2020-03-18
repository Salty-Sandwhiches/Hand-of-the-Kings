package nl.looplan.hotk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_bank.*

import nl.looplan.hotk.R
import nl.looplan.hotk.data.Player
import nl.looplan.hotk.tools.TableViewAdapter

/**
 * A simple [Fragment] subclass.
 */
class BankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TableViewAdapter()
        fragment_bank_table_view.adapter = adapter

        FirebaseFirestore.getInstance()
            .collection("players")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val result = task.result!!
                    val players = result.toObjects(Player::class.java)
                    val playerNames = players.map { player -> player.name }
                    adapter.setAllItems(playerNames, listOf("2", "2"), listOf())
                }
            }
    }

}
