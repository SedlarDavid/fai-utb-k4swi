package repository

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import api.Api
import api.LocationApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import entities.forecast.Forecast
import kotlinx.coroutines.runBlocking
import serializers.entities.Location
import services.SystemService
import javax.inject.Inject

class LocationRepository @Inject constructor() {

    private val api: LocationApi = Api.location


    suspend fun resolveInitialLocation(): Location {
        if (SystemService.getLocation() == null) {
            val response = api.getDefaultLocation()
            return response
        }
        if (SystemService.getLocation()?.latitude == null || SystemService.getLocation()?.longitude == null) {
            val response = api.getLocationByPlace(SystemService.getLocation()!!.city!!)

            val decoded = Location(city = response, latitude = null, longitude = null)
            return decoded
        } else {
            val lat = SystemService.getLocation()!!.latitude!!
            val lng = SystemService.getLocation()!!.longitude!!
            val response = api.getLocationByLatLng(
                lat, lng
            )
            val decoded = Location(city = response, latitude = lat, longitude = lng)
            return decoded
        }

    }

}