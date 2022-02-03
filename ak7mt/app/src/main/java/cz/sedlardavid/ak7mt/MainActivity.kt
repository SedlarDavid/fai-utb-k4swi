package cz.sedlardavid.ak7mt

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cz.sedlardavid.ak7mt.databinding.ActivityForecastBinding
import cz.sedlardavid.ak7mt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import repository.LocationRepository
import serializers.viewmodels.LocationViewModel
import serializers.viewmodels.SettingsViewModel
import viewmodels.ForecastViewModel
import java.util.*
import kotlin.concurrent.schedule
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    private lateinit var binding: ActivityMainBinding


    private val model: LocationViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        model.location.observe(this) {
            //3
            rerouteToDashboard()
        }


        //1
        settingsViewModel.settings.observe(this) {
            //2
            launch { model.resolveLocation() }
        }


    }

    private fun rerouteToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        this.finish()
    }
}