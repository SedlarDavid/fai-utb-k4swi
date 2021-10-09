package cz.sedlardavid.ak7mt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


private const val SETTINGS_FILE_NAME = "settings.pb";
/*
private val Context.settingsStore: DataStore<Settings> by dataStore(
    fileName = SETTINGS_FILE_NAME,
    serializer = SettingsSerializer
)
*/

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

    }

}