package cz.sedlardavid.eventorr.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sedlardavid.eventorr.entities.Event

class EventModel(val event: Event) {

    private val _isFav = MutableLiveData(false)

    val isFav: LiveData<Boolean> = _isFav

    fun setIsFav(isFav: Boolean) {
        _isFav.value = isFav
    }

}