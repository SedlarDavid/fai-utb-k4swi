package cz.sedlardavid.eventorr.repositories

import android.content.Context
import cz.sedlardavid.eventorr.Performer
import cz.sedlardavid.eventorr.api.EventsApi
import cz.sedlardavid.eventorr.data.eventFavoritesDataStore
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.mocks.EventsResponseMock
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

class EventsRepository @Inject() constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getEvents(): List<Event> {
        val eventResponse = EventsResponseMock.mock()
        try {
            val data = EventsApi.retrofitService.getEvents()
        } catch (e: Exception) {
            var tmp = e
            Logger.getAnonymousLogger().log(Level.WARNING, e.toString())
        }
        return eventResponse.events
    }

    val eventFavoritesFlow = context.eventFavoritesDataStore.data

    suspend fun addToFavorites(event: Event) {
        context.eventFavoritesDataStore.updateData { favorites ->
            favorites.toBuilder().addFavorites(
                cz.sedlardavid.eventorr.Event.newBuilder()
                    .setCreatedAt(event.created_at)
                    .setDatetimeLocal(event.datetime_local)
                    .setDatetimeTbd(event.datetime_tbd)
                    .setDatetimeUtc(event.datetime_utc)
                    .setDescription(event.description)
                    .setId(event.id)
                    .setTitle(event.title)
                    .setType(event.type)
                    .setUrl(event.url)
                    .setShortTitle(event.short_title)
                    .addAllPerformers(event.performers.map { p -> Performer.newBuilder().setImage(p.image).build() })
            ).build()
        }
    }

    fun removeFromFavorites(event: Event) {
        TODO("Not yet implemented")
    }
}