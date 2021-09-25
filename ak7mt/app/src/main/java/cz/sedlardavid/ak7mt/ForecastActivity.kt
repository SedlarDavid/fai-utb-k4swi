package cz.sedlardavid.ak7mt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ForecastActivity : AppCompatActivity() {

    private val forecastViewModel = viewmodels.ForecastViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

    }
}