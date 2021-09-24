package cz.sedlardavid.ak7mt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        val btnForecast = findViewById<Button>(R.id.btnForecast);
        val btnHistory = findViewById<Button>(R.id.btnHistory);
        val btnSettings = findViewById<Button>(R.id.btnSettings);

        btnForecast.setOnClickListener {
            toForecast();
        };
        btnHistory.setOnClickListener {
            toHistory();
        };
        btnSettings.setOnClickListener {
            toSettings();
        };
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