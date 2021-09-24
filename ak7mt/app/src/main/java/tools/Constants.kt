package tools

object Constants {

    const val WEATHER_API_KEY: String = "5a32783cab327da3665110a93ecf2117"
    const val WEATHER_URL: String = "https://api.darksky.net/forecast/"
    const val GEO_API_KEY: String = "5366f5f2cbb54b2b8adc93a8130f40f8"
    const val GEO_URL: String = "https://api.opencagedata.com/geocode/v1/json?q="
}

class UrlBuilder {
    companion object {
        fun buildForecastURL(lat: Double? = null, lng: Double? = null): String {
            var url = Constants.WEATHER_URL
            return url
        }


        fun buildLocationURL(
            lat: Double? = null,
            lng: Double? = null,
            place: String? = null
        ): String {
            var url = Constants.GEO_URL

            if (lat != null && lng != null) {
                url += "$lat+$lng"
            }
            if (place != null) {
                url += "$place"
            }

            url += "&key=${Constants.GEO_API_KEY}"
            return url
        }
    }
}