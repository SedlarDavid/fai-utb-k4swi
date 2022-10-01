package cz.sedlardavid.eventorr.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.sedlardavid.eventorr.entities.Event
import cz.sedlardavid.eventorr.repositories.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(repo: EventsRepository) : ViewModel() {

    private val _events = MutableLiveData<List<Event>>(listOf())
    val events: LiveData<List<Event>> = _events

    init {

        _events.value = repo.getEvents()

    }


}