package cz.sedlardavid.eventorr.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.entities.Performer
import cz.sedlardavid.eventorr.repositories.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(private val repo: EventsRepository) : ViewModel() {

    private val _events = repo.events
    private val _favorites = MutableLiveData(ArrayList<Event>())

    val events: LiveData<List<Event>> = _events
    val favorites: LiveData<ArrayList<Event>> = _favorites

    init {
        viewModelScope.launch {
            repo.eventFavoritesFlow.collect { favs ->
                _favorites.value = ArrayList(favs.favoritesList.map { event ->
                    Event(
                        id = event.id,
                        created_at = event.createdAt,
                        datetime_local = event.datetimeLocal,
                        datetime_tbd = event.datetimeTbd,
                        datetime_utc = event.datetimeUtc,
                        description = event.description,
                        title = event.title,
                        type = event.type,
                        url = event.url,
                        short_title = event.shortTitle,
                        performers = ArrayList(event.performersList.map { performer ->
                            Performer(
                                image = performer.image
                            )
                        })
                    )
                })
                synchronized(_favorites) {
                    _favorites.notify()
                }
            }
        }
    }


    fun getEvents() {
        viewModelScope.launch {
            repo.getEvents()
        }
    }

    fun addToFavorites(event: Event) {
        _favorites.value?.add(event)

        viewModelScope.launch {
            repo.addToFavorites(event)
        }
    }

    fun removeFromFavorites(event: Event) {
        val toRemove = _favorites.value?.filter { e -> e.id == event.id }?.get(0)
        _favorites.value?.remove(toRemove)
        synchronized(_favorites) {
            _favorites.notify()
        }
        viewModelScope.launch {
            repo.removeFromFavorites(event)
        }
    }

}