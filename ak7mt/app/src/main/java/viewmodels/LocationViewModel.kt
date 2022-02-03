package serializers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import entities.forecast.Forecast
import kotlinx.coroutines.launch

import repository.ForecastRepository
import repository.LocationRepository
import serializers.entities.Location
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(private val repo: LocationRepository) : ViewModel() {

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

/*    init {
        viewModelScope.launch {
            _location.value = repo.resolveInitialLocation()
        }
    }*/


    suspend fun resolveLocation(){

        _location.value = repo.resolveInitialLocation()
    }

}