package cz.sedlardavid.eventorr.repositories

import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.mocks.EventsResponseMock

class EventsRepository {
    fun getEvents(): List<Event> {
        val eventResponse = EventsResponseMock.mock()

        return eventResponse.events
    }
}