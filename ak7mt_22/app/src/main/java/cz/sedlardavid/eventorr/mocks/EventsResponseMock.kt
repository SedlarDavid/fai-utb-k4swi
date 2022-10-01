package cz.sedlardavid.eventorr.mocks

import com.google.gson.GsonBuilder
import cz.sedlardavid.eventorr.entities.EventsResponse

class EventsResponseMock {

    companion object {
        fun mock(): EventsResponse {
            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

            return gson.fromJson(sampleMockData, EventsResponse::class.java)

        }
    }

}