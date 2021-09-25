package cz.sedlardavid.ak7mt

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import repository.LocationRepository
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //TODO handle init app state reroute to dashboard
        // Get location permission
        //  - Load city from storage ?? continue to app

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        val locRepository = LocationRepository(this)

        Timer().schedule(3000) {
            rerouteToDashboard()
        }


    }

    private fun rerouteToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        this.finish()
    }
}