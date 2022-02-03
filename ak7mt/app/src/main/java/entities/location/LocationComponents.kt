package serializers.entities.location

import com.google.gson.annotations.SerializedName

data class LocationComponents(
    val city: String,
    val country: String,
    @SerializedName("country_code") val countryCode: String,
)