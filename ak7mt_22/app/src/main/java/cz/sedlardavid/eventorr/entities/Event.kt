package cz.sedlardavid.eventorr.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class Event(
    val id: Int,
    val type: String,
    @SerializedName("datetime_utc")
    val dateTime: Date,
    @SerializedName("short_title")
    val shortTitle: String,
)
