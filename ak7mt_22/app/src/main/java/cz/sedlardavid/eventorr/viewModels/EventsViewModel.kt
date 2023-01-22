package cz.sedlardavid.eventorr.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.entities.Performer
import cz.sedlardavid.eventorr.models.EventModel
import cz.sedlardavid.eventorr.repositories.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(private val repo: EventsRepository) : ViewModel() {

    private val _events = repo.events
    private val _favorites = MutableLiveData(ArrayList<EventModel>())

    val events: LiveData<List<EventModel>> = _events
    val favorites: LiveData<ArrayList<EventModel>> = _favorites

    init {
        viewModelScope.launch {
            repo.eventFavoritesFlow.collect { favs ->
                _favorites.value = ArrayList(favs.favoritesList.map { event ->
                    val model =
                        EventModel(
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
                        )
                    model.setIsFav(true)
                    model
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

    fun addToFavorites(model: EventModel) {
        _favorites.value?.add(model)

        model.setIsFav(true)
        viewModelScope.launch {
            repo.addToFavorites(model.event)
        }

    }

    fun removeFromFavorites(model: EventModel) {
        val toRemove = _favorites.value?.filter { e -> e.event.id == model.event.id }?.get(0)
        _favorites.value?.remove(toRemove)
        model.setIsFav(false)
        viewModelScope.launch {
            repo.removeFromFavorites(model.event)
        }
    }

}