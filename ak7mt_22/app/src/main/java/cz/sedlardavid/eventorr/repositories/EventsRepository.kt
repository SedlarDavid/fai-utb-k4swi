package cz.sedlardavid.eventorr.repositories

import androidx.datastore.core.DataStore
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.mocks.EventsResponseMock
import javax.inject.Inject

class EventsRepository @Inject() constructor(private val eventFavoritesStore: DataStore<cz.sedlardavid.eventorr.EventFavorites>) {
    fun getEvents(): List<Event> {
        val eventResponse = EventsResponseMock.mock()

        return eventResponse.events
    }

    val eventFavoritesFlow = eventFavoritesStore.data


    suspend fun addToFavorites(event: Event) {
        eventFavoritesStore.updateData { favorites ->
            favorites.toBuilder().addFavorites(
                cz.sedlardavid.eventorr.Event.newBuilder()
                    .setTitle(event.title)
                    .setCreatedAt(event.created_at)
            ).build()
        }
    }
}