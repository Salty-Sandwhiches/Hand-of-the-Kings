package nl.looplan.hotk.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.looplan.hotk.data.Kingdom
import nl.looplan.hotk.data.Land

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val successfulFetch =
                withContext(Dispatchers.Default) { Kingdom.fetchKingdoms() } && withContext(Dispatchers.Default) { Land.fetchLands() }

            if(successfulFetch) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}
