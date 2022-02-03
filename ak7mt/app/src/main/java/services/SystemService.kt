package services

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import cz.sedlardavid.ak7mt.Settings
import serializers.SettingsSerializer
import serializers.entities.Location


object SystemService {


    private val units: Settings.Units = Settings.Units.IMPERIAL
    private var location: Location? = null

    fun getUnitsSymbol(): String {
        if (units.equals("si")) {
            return "°C"
        } else {
            return "F"
        }
    }

    fun getLocation(): Location? {
        return location
    }

    fun getUnits(): Settings.Units {
        return units
    }

    fun setLocation(location: Location) {
        this.location = location
    }

}