package serializers.viewmodels

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.sedlardavid.ak7mt.Settings
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import serializers.SettingsSerializer
import serializers.entities.Location
import services.SystemService
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


 val SETTINGS_FILE_NAME = "settings.pb";
 val Context.settingsStore: DataStore<Settings> by dataStore(
    fileName = SETTINGS_FILE_NAME, serializer = SettingsSerializer
)


@HiltViewModel
class SettingsViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {


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

                SystemService.setLocation(Location(city = sett.primaryCity, latitude = null, longitude = null))

                _settings.value = sett

            }

        }
    }

}