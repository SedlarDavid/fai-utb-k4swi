package api

import io.ktor.client.*


object Api {
    private val client = HttpClient()

    val forecast: ForecastApi = ForecastApi(client)
    val location: LocationApi = LocationApi(client)
}