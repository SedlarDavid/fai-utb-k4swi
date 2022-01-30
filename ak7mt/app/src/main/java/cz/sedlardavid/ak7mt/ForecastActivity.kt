package cz.sedlardavid.ak7mt


import adapters.ForecastAdapter
import android.os.Bundle
import android.provider.Contacts
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cz.sedlardavid.ak7mt.databinding.ActivityForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import entities.forecast.Forecast
import serializers.entities.forecast.ForecastData
import viewmodels.ForecastViewModel

@AndroidEntryPoint
class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding

    private val model: ForecastViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val loader: ProgressBar = binding.loaForecast
        val forecast = binding.hourlyForecastList


        forecast.visibility = View.GONE
        loader.visibility = View.VISIBLE


        model.forecast.observe(this, Observer<Forecast> {
            val llm = LinearLayoutManager(this)
            llm.orientation = LinearLayoutManager.VERTICAL
            val recyclerView = binding.hourlyForecastList
            val dataList = model.forecast.value?.hourly?.data?.map {
                it
            }?.toTypedArray()
            val adapterList = dataList ?: Array<ForecastData>(0) { ForecastData(0, "", "", 0.0) }
            val adapter = ForecastAdapter(adapterList, context = baseContext)
            recyclerView.layoutManager = llm
            recyclerView.adapter = adapter


            loader.visibility = View.GONE
            forecast.visibility = View.VISIBLE
        })


    }


}