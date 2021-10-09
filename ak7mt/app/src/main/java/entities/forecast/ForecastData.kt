package serializers.entities.forecast

data class ForecastData(
    val time: Int,
    val summary: String,
    val icon: String,
    val temperature: Double
)