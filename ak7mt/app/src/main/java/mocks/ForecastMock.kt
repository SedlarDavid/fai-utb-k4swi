package serializers.mocks

import entities.forecast.Forecast
import serializers.entities.forecast.ForecastData
import serializers.entities.forecast.HourlyForecast

class ForecastMock {
    companion object {
        val forecast: Forecast = Forecast(
            49.22645, 17.67065, ForecastData(50000, "Hot AF", "sun", 38.0), HourlyForecast(
                listOf()
            )
        )
    }
}