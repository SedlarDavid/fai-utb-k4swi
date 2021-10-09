package api

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import tools.UrlBuilder

class LocationApi(private val client: HttpClient) {


    fun getLocationByLatLng(lat: Double, lng: Double): String {
        var response: String = ""
        println(response)
        runBlocking {
            getLocationByLatLngAsync(lat, lng) { v -> response = v }
        }
        return response
    }

    fun getLocationByPlace(place: String): String {
        var response: String = ""
        println(response)
        runBlocking {
            getLocationByPlaceAsync(place) { v -> response = v }
        }
        return response
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
