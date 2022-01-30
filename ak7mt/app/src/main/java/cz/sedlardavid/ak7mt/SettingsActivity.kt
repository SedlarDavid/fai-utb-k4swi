package cz.sedlardavid.ak7mt

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import cz.sedlardavid.ak7mt.databinding.ActivityDashboardBinding
import cz.sedlardavid.ak7mt.databinding.ActivitySettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import serializers.SettingsSerializer
import java.util.concurrent.Flow
import kotlin.coroutines.CoroutineContext

private const val SETTINGS_FILE_NAME = "settings.pb";
private val Context.settingsStore: DataStore<Settings> by dataStore(
    fileName = SETTINGS_FILE_NAME,
    serializer = SettingsSerializer
)


class SettingsActivity : AppCompatActivity(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private lateinit var binding: ActivitySettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val btnSave = binding.btnSaveSettings
        val loader = binding.loaSetting
        val city = binding.texEditText

        settingsStore.data.map { settings ->
            city.setText(settings.primaryCity)
        }

        btnSave.setOnClickListener {
            launch { saveSettings(loader) }
        }

    }


    suspend fun saveSettings(loader: ProgressBar) {


        loader.visibility = View.VISIBLE
        settingsStore.updateData { currentSettings ->
            currentSettings.toBuilder().setPrimaryCity("Olomouc").build()
            currentSettings.toBuilder().setUnits(Settings.Units.METRIC).build()
        }

        loader.visibility = View.GONE
    }
}