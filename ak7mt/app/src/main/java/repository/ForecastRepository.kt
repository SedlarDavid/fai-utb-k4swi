package repository

import api.Api
import api.ForecastApi
import com.google.gson.Gson
import entities.forecast.Forecast
import services.SystemService
import javax.inject.Inject

class ForecastRepository @Inject constructor() {
    private val api: ForecastApi = Api.forecast
    private val gson = Gson()

    suspend fun getForecast(): Forecast {

        val response = api.getForecast(SystemService.getForecastLatitude() ,SystemService.getForecastLongitude())
        val decoded = gson.fromJson(response, Forecast::class.java)
        return decoded

        /*return ForecastMock.forecast*/
    }


}