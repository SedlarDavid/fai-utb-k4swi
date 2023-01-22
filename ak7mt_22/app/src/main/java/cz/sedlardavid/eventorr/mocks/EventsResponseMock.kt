package cz.sedlardavid.eventorr.mocks

import com.google.gson.GsonBuilder
import cz.sedlardavid.eventorr.entities.EventsResponse
import cz.sedlardavid.eventorr.tools.DATE_FORMAT

class EventsResponseMock {

    companion object {
        fun mock(): EventsResponse {
            val gson = GsonBuilder().setDateFormat(DATE_FORMAT).create()

            return gson.fromJson(sampleMockData, EventsResponse::class.java)

        }
    }

}