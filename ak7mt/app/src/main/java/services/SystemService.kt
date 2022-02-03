package services

import cz.sedlardavid.ak7mt.Settings
import serializers.entities.Location


object SystemService {


     const val defaultLat: Double = 49.224438;
     const val defaultLng: Double = 17.67065;
    private const val defaultCity: String = "Zlín";


    private var units: Settings.Units = Settings.Units.IMPERIAL
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

    fun getForecastLatitude(): Double {
        return location?.geometry?.latitude ?: defaultLat
    }

    fun getForecastLongitude(): Double {
        return location?.geometry?.longitude ?: defaultLng
    }

    fun getForecastCity(): String {

        return location?.components?.city ?: defaultCity

    }

    fun setLocation(location: Location) {
        this.location = location
    }

    fun setUnits(units: Settings.Units) {
        this.units = units
    }


}