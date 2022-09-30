package cz.sedlardavid.eventorr.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Event(
    val id: Int,
    val type: String,
/*    @SerialName("datetime_utc")
    val dateTime: UtcOffset,*/
    @SerialName("short_title") val shortTitle: String,
)
