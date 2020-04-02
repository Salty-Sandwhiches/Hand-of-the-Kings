package nl.looplan.hotk.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import nl.looplan.hotk.R
import nl.looplan.hotk.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            val mainViewModel: MainViewModel by viewModels()
            mainViewModel.createGame()
        }

    }
}
