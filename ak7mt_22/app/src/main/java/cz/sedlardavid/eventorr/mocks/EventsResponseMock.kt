package cz.sedlardavid.eventorr.mocks

import cz.sedlardavid.eventorr.entities.EventsResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class EventsResponseMock {

    companion object {
        fun mock(): EventsResponse {

            return Json { ignoreUnknownKeys = true }.decodeFromString(sampleMockData)

        }
    }

}