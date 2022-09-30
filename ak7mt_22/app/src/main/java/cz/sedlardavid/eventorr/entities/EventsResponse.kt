package cz.sedlardavid.eventorr.entities

import kotlinx.serialization.Serializable

@Serializable
data class EventsResponse(val events: List<Event>)
