package serializers.entities.location

import com.google.gson.annotations.SerializedName

data class LocationGeometry(

    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
)
