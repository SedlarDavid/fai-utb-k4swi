package cz.sedlardavid.eventorr.repositories

import android.content.Context
import cz.sedlardavid.eventorr.data.eventFavoritesDataStore
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.mocks.EventsResponseMock
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EventsRepository @Inject() constructor(
    @ApplicationContext private val context: Context
) {
    fun getEvents(): List<Event> {
        val eventResponse = EventsResponseMock.mock()

        return eventResponse.events
    }

    val eventFavoritesFlow = context.eventFavoritesDataStore.data

    suspend fun addToFavorites(event: Event) {
        context.eventFavoritesDataStore.updateData { favorites ->
            favorites.toBuilder().addFavorites(
                cz.sedlardavid.eventorr.Event.newBuilder().setTitle(event.title).setCreatedAt(event.created_at)
            ).build()
        }
    }
}