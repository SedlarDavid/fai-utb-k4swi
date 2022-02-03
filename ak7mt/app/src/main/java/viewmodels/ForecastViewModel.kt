package viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import entities.forecast.Forecast
import kotlinx.coroutines.launch

import repository.ForecastRepository
import javax.inject.Inject


@HiltViewModel
class ForecastViewModel @Inject constructor(repo: ForecastRepository) : ViewModel() {

    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast> = _forecast

    init {
        viewModelScope.launch {
            _forecast.value = repo.getForecast()
        }
    }


}