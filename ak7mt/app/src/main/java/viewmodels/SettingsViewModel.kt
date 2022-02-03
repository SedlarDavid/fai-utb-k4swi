package serializers.viewmodels

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.sedlardavid.ak7mt.R
import cz.sedlardavid.ak7mt.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import repository.LocationRepository
import serializers.SettingsSerializer
import serializers.entities.Location
import serializers.entities.location.LocationComponents
import serializers.entities.location.LocationGeometry
import services.SystemService
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


val SETTINGS_FILE_NAME = "settings.pb";
val Context.settingsStore: DataStore<Settings> by dataStore(
    fileName = SETTINGS_FILE_NAME, serializer = SettingsSerializer
)


@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationRepository: LocationRepository
) : ViewModel() {


    private val _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings> = _settings

    init {
        viewModelScope.launch {
            val settFlow = context.settingsStore.data.catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    Log.e(ContentValues.TAG, "Error reading preferences.", exception)
                    emit(Settings.getDefaultInstance())
                } else {
                    throw exception
                }
            }

            settFlow.collect { sett ->

                updateSystemService(sett)

                _settings.value = sett

            }

        }
    }


    suspend fun saveSettings(loader: ProgressBar, city: String, units: Settings.Units) {
        if (city.isEmpty()) {
            Toast.makeText(
                context, context.getString(R.string.emptyCity),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        loader.visibility = View.VISIBLE

        val newLoca = locationRepository.getLocationByCity(city)

        val newSettings = context.settingsStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setCity(newLoca.components.city)
                .setCountry(newLoca.components.country)
                .setCountryCode(newLoca.components.countryCode)
                .setLatitude(newLoca.geometry.latitude)
                .setLongitude(newLoca.geometry.longitude)
                .setUnits(units)
                .build()
        }

        updateSystemService(newSettings)

        loader.visibility = View.GONE

        Toast.makeText(
            context, context.getString(R.string.saved),
            Toast.LENGTH_LONG
        ).show()
    }


    private fun updateSystemService(sett: Settings) {
        SystemService.setLocation(
            Location(
                LocationGeometry(latitude = sett.latitude, longitude = sett.longitude),
                LocationComponents(
                    city = sett.city,
                    country = sett.country,
                    countryCode = sett.countryCode,
                ),
            )
        )
        SystemService.setUnits(sett.units)
    }
}