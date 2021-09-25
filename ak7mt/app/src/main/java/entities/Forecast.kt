package entities

import com.google.gson.annotations.SerializedName

data class Forecast(
    val latitude: Double,
    val longitude: Double,
    @SerializedName("currently") val current: ForecastData,
    val hourly: HourlyForecast
)

data class HourlyForecast(
    val data: List<ForecastData>,
)

data class ForecastData(
    val time: Int,
    val summary: String,
    val icon: String,
    val temperature: Double
)