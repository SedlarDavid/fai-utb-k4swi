package api

import com.google.gson.Gson
import entities.forecast.Forecast
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import serializers.entities.Location
import serializers.entities.location.LocationComponents
import serializers.entities.location.LocationGeometry
import services.SystemService
import tools.UrlBuilder

class LocationApi(private val client: HttpClient) {

    private val gson = Gson()
    suspend fun getLocationByLatLng(lat: Double, lng: Double): Location {
        var response = ""
        println(response)

        getLocationByLatLngAsync(lat, lng) { v -> response = v }
        val jObject = JSONObject(response)
        val properResult = jObject.getJSONArray("results").get(0)
        val newJsonString = properResult.toString()
        val decoded = gson.fromJson(newJsonString, Location::class.java)
        return decoded
    }

    suspend fun getLocationByPlace(place: String): Location {
        var response = ""
        println(response)

        getLocationByPlaceAsync(place) { v -> response = v }
        val jObject = JSONObject(response)
        val properResult = jObject.getJSONArray("results").get(0)
        val newJsonString = properResult.toString()
        val decoded = gson.fromJson(newJsonString, Location::class.java)
        return decoded
    }

    suspend fun getDefaultLocation(): Location {
        var response = ""
        println(response)

        getLocationByLatLngAsync(SystemService.defaultLat, SystemService.defaultLng) { v -> response = v }
        val jObject = JSONObject(response)
        val properResult = jObject.getJSONArray("results").get(0)
        val newJsonString = properResult.toString()
        val decoded = gson.fromJson(newJsonString, Location::class.java)
        return decoded
    }

    private suspend fun getLocationByLatLngAsync(
        lat: Double,
        lng: Double,
        onFinish: (resp: String) -> Unit
    ) {
        val url = UrlBuilder.buildLocationURL(lat, lng)
        val response = client.get<String>(url)
        onFinish(response)
    }

    private suspend fun getLocationByPlaceAsync(
        place: String,
        onFinish: (resp: String) -> Unit
    ) {
        val url = UrlBuilder.buildLocationURL(place = place)
        val response = client.get<String>(url)
        onFinish(response)
    }
}
