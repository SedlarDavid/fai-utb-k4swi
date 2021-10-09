package api

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import tools.Constants

class ForecastApi(private val client: HttpClient) {


    fun getForecast(lat: Double, lng: Double): String {
        var response: String = ""
        println(response)
        runBlocking {
            getForecastAsync(lat, lng) { v -> response = v }
        }
        return response
    }

    private suspend fun getForecastAsync(
        lat: Double,
        lng: Double,
        onFinish: (resp: String) -> Unit
    ) {
        val response = client.get<String>("${Constants.WEATHER_URL}${Constants.WEATHER_API_KEY}/$lat,$lng")
        onFinish(response)
    }
}
