package api

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import serializers.entities.Location
import tools.UrlBuilder

class LocationApi(private val client: HttpClient) {


    suspend fun getLocationByLatLng(lat: Double, lng: Double): String {
        var response: String = ""
        println(response)

        getLocationByLatLngAsync(lat, lng) { v -> response = v }

        return response
    }

    suspend fun getLocationByPlace(place: String): String {
        var response: String = ""
        println(response)

        getLocationByPlaceAsync(place) { v -> response = v }

        return response
    }

    suspend fun getDefaultLocation(): Location {
        var response: String = ""
        println(response)

        getLocationByLatLngAsync(49.224438, 17.662764) { v -> response = v }

        return Location(49.224438, 17.662764, "Zlín")
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
