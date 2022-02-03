package serializers.entities

import com.google.gson.annotations.SerializedName
import serializers.entities.location.LocationComponents
import serializers.entities.location.LocationGeometry

data class Location(
    @SerializedName("geometry") val geometry: LocationGeometry,
    @SerializedName("components") val components: LocationComponents
)


