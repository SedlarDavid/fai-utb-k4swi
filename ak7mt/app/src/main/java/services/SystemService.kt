package services

import cz.sedlardavid.ak7mt.Settings
import serializers.entities.Location


object SystemService {


    const val defaultLat: Double = 49.224438;
    const val defaultLng: Double = 17.67065;
    private const val defaultCity: String = "Zlín";


    private var units: Settings.Units = Settings.Units.METRIC
    private var location: Location? = null

    val isEmpty: Boolean
        get() {
            return if (location == null) {
                true
            } else {
                location!!.components.city.isEmpty() && location!!.components.country.isEmpty() && location!!.components.countryCode.isEmpty() && location!!.geometry.latitude.equals(
                    0.0
                ) && location!!.geometry.longitude.equals(0.0)
            }
        }

    fun getUnitsSymbol(): String {
        if (units == Settings.Units.METRIC) {
            return "°C"
        } else {
            return "F"
        }
    }


    fun getUnits(): String {
        if (units == Settings.Units.METRIC) {
            return "si"
        } else {
            return "us"
        }
    }

    fun getLocation(): Location? {
        return location
    }

    fun getForecastLatitude(): Double {
        return if (location?.geometry?.latitude == null || location!!.geometry.latitude.equals(0.0))
            defaultLat
        else {
            location!!.geometry.latitude
        }
    }

    fun getForecastLongitude(): Double {
        return if (location?.geometry?.longitude == null || location!!.geometry.longitude.equals(0.0))
            defaultLng
        else {
            location!!.geometry.longitude
        }
    }

    fun getForecastCity(): String {

        return if (location?.components?.city == null || location!!.components.city.isEmpty()) {
            defaultCity
        } else {
            location!!.components.city
        }

    }

    fun setLocation(location: Location) {
        this.location = location
    }

    fun setUnits(units: Settings.Units) {
        this.units = units
    }


}