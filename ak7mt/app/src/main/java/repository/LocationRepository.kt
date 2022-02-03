package repository


import api.Api
import api.LocationApi

import serializers.entities.Location
import services.SystemService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor() {

    private val api: LocationApi = Api.location


    suspend fun resolveInitialLocation(): Location {
        if (SystemService.getLocation() == null) {
            return api.getDefaultLocation()
        }
        return if (SystemService.getLocation()?.geometry?.latitude == null || SystemService.getLocation()?.geometry?.longitude == null) {
            val response = api.getLocationByPlace(SystemService.getLocation()!!.components.city)
            response
        } else {
            val lat = SystemService.getLocation()!!.geometry.latitude
            val lng = SystemService.getLocation()!!.geometry.longitude
            val response = api.getLocationByLatLng(
                lat, lng
            )
            response
        }

    }

    suspend fun getLocationByCity(city:String):Location{
       return api.getLocationByPlace(city)
    }

}