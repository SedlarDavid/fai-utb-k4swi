package entities.forecast

import com.google.gson.annotations.SerializedName
import serializers.entities.forecast.ForecastData
import serializers.entities.forecast.HourlyForecast

data class Forecast(
    val latitude: Double,
    val longitude: Double,
    @SerializedName("currently") val current: ForecastData,
    val hourly: HourlyForecast
)



