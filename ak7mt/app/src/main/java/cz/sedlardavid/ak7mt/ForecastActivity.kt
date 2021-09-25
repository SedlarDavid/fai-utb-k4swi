package cz.sedlardavid.ak7mt


import adapters.ForecastAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ForecastActivity : AppCompatActivity() {

    private val forecastViewModel = viewmodels.ForecastViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = findViewById<RecyclerView>(R.id.hourlyForecastList)
        val dataList = forecastViewModel.forecast.value?.hourly?.data?.map {
            it.summary
        }?.toTypedArray();
        val adapterList = dataList ?: Array<String>(0) { "" };
        val adapter = ForecastAdapter(adapterList)
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter

    }
}