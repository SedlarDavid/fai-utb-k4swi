package cz.sedlardavid.ak7mt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.sedlardavid.ak7mt.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val btnForecast = binding.btnForecast
        val btnHistory = binding.btnHistory
        val btnSettings = binding.btnSettings

        btnForecast.setOnClickListener {
            toForecast()
        }
        btnHistory.setOnClickListener {
            toHistory()
        }
        btnSettings.setOnClickListener {
            toSettings()
        }
    }

    private fun toForecast() {

        startActivity(Intent(this, ForecastActivity::class.java))
    }

    private fun toHistory() {

        startActivity(Intent(this, HistoryActivity::class.java))
    }

    private fun toSettings() {

        startActivity(Intent(this, SettingsActivity::class.java))
    }
}