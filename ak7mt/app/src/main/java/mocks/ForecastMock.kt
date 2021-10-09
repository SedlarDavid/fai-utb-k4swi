package serializers.mocks

import entities.forecast.Forecast
import entities.ForecastData
import entities.HourlyForecast

class ForecastMock {
    companion object {
        val forecast: Forecast = Forecast(
            49.22645, 17.67065, ForecastData(50000, "Hot AF", "sun", 38.0), HourlyForecast(
                listOf()
            )
        )
    }
}