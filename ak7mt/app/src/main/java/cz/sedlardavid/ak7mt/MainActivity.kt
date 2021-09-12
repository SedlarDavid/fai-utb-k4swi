package cz.sedlardavid.ak7mt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import api.Api
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //TODO handle init app state reroute to dashboard
        // Get location permission
        //  - Load city from storage ?? continue to app
        Api()
        Timer().schedule(3000) {
            rerouteToDashboard()
        }


    }

    private fun rerouteToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        this.finish()
    }
}