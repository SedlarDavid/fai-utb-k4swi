package cz.sedlardavid.ak7mt

import android.R.attr.data
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.google.android.material.internal.ContextUtils.getActivity
import cz.sedlardavid.ak7mt.databinding.ActivitySettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import serializers.SettingsSerializer
import serializers.viewmodels.settingsStore
import java.io.IOException
import kotlin.coroutines.CoroutineContext


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
        val units = binding.spiUnits


        loader.visibility = View.VISIBLE

        ArrayAdapter.createFromResource(
            this,
            R.array.units,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            units.adapter = adapter
        }

        val settFlow = settingsStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    Log.e(TAG, "Error reading sort order preferences.", exception)
                    emit(Settings.getDefaultInstance())
                } else {
                    throw exception
                }
            }


        launch {
            collectSettingsFlow(settFlow, city, units, loader)
        }


        btnSave.setOnClickListener {
            city.clearFocus()
            val cityData = city.text.toString()
            val unitsData = spinnerDataToUnits(units.selectedItem)
            launch { saveSettings(loader, cityData, unitsData) }
        }

    }

    private suspend fun collectSettingsFlow(
        settFlow: Flow<Settings>,
        city: EditText,
        units: Spinner,
        loader: ProgressBar
    ) {

        settFlow.collect { sett ->
            city.setText(sett.primaryCity)
            units.setSelection(dataToUnits(sett.units))

            loader.visibility = View.GONE
        }


    }

    private fun dataToUnits(units: Settings.Units?): Int {
        return when (units) {
            Settings.Units.IMPERIAL -> 0
            else -> 1
        }

    }

    private fun spinnerDataToUnits(selectedItem: Any?): Settings.Units {
        val isImperial = R.array.units == selectedItem
        val units: Settings.Units = if (isImperial) {
            (Settings.Units.IMPERIAL)
        } else {
            Settings.Units.METRIC
        }
        return units
    }


    private suspend fun saveSettings(loader: ProgressBar, city: String, units: Settings.Units) {
        if (city.isEmpty()) {
            Toast.makeText(
                this, getString(R.string.emptyCity),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        loader.visibility = View.VISIBLE
        settingsStore.updateData { currentSettings ->
            currentSettings.toBuilder().setPrimaryCity(city).setUnits(units).build()
        }

        loader.visibility = View.GONE

        Toast.makeText(
            this, getString(R.string.saved),
            Toast.LENGTH_LONG
        ).show()
    }
}