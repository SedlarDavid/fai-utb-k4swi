package repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import entities.Settings
import serializers.SettingsSerializer

private const val SETTINGS_KEY = "settings";

private val Context.settingsStore by dataStore(
    fileName = "settings",
    serializer = SettingsSerializer,
)
class SettingRepository(private val settingsStore: DataStore<Settings>, context: Context) {


}