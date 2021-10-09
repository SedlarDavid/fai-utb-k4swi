package cz.sedlardavid.ak7mt


import adapters.ForecastAdapter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cz.sedlardavid.ak7mt.databinding.ActivityForecastBinding
import viewmodels.ForecastViewModel


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
            it.summary
        }?.toTypedArray()
        val adapterList = dataList ?: Array<String>(0) { "" }
        val adapter = ForecastAdapter(adapterList)
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter

    }
}