package api

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*


class Api {
    private val client = HttpClient()

    val forecast: ForecastApi = ForecastApi(client)


}

class ForecastApi(client: HttpClient) {

    val client: HttpClient = client;


    fun getForecast()  {
        val lat = "42.3601"
        val lng = "-71.0589"
        val apiKey = "0123456789abcdef9876543210fedcba"
        //getForecastAsync()

    }

    private suspend fun getForecastAsync() {
        val response = null;
           // client.get("https://api.darksky.net/forecast/$apiKey/$lat,$lng")

    }
}