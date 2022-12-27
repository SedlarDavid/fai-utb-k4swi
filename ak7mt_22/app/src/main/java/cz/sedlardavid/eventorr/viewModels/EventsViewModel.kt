package cz.sedlardavid.eventorr.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.repositories.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(private val repo: EventsRepository) : ViewModel() {

    private val _events = MutableLiveData<List<Event>>(listOf())
    private val _favorites = MutableLiveData<ArrayList<Event>>(ArrayList<Event>())
    val events: LiveData<List<Event>> = _events

    init {
        _events.value = repo.getEvents()
    }


    fun addToFavorites(event: Event) {
        _favorites.value?.add(event)

        viewModelScope.launch {
            repo.addToFavorites(event)
        }
    }

}