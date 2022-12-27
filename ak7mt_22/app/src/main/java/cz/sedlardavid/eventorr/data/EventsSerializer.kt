package cz.sedlardavid.eventorr.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import cz.sedlardavid.eventorr.Event
import cz.sedlardavid.eventorr.EventFavorites
import java.io.InputStream
import java.io.OutputStream

object EventSerializer : Serializer<Event> {
    override val defaultValue: Event = Event.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Event {
        try {
            return Event.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: Event,
        output: OutputStream
    ) = t.writeTo(output)
}

object EventFavoritesSerializer : Serializer<EventFavorites> {
    override val defaultValue: EventFavorites = EventFavorites.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): EventFavorites {
        try {
            return EventFavorites.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: EventFavorites,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.eventDataStore: DataStore<Event> by dataStore(
    fileName = "event.pb",
    serializer = EventSerializer
)
val Context.eventFavoritesDataStore: DataStore<EventFavorites> by dataStore(
    fileName = "eventFavorites.pb",
    serializer = EventFavoritesSerializer
)
