package serializers.entities.forecast

import entities.ForecastData

data class HourlyForecast(
    val data: List<ForecastData>,
)
