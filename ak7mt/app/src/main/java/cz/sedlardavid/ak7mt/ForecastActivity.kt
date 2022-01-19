package cz.sedlardavid.ak7mt


import adapters.ForecastAdapter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cz.sedlardavid.ak7mt.databinding.ActivityForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import serializers.entities.forecast.ForecastData
import viewmodels.ForecastViewModel

@AndroidEntryPoint
class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val model: ForecastViewModel by viewModels()

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = binding.hourlyForecastList
        val dataList = model.forecast.value?.hourly?.data?.map {
            it
        }?.toTypedArray()
        val adapterList = dataList ?: Array<ForecastData>(0) { ForecastData(0,"","",0.0) }
        val adapter = ForecastAdapter(adapterList)
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter

    }
}