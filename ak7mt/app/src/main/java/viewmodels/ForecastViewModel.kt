package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import entities.Forecast
import repository.ForecastRepository


class ForecastViewModel : ViewModel() {
    private val repo = ForecastRepository();
    var forecast: LiveData<Forecast> = MutableLiveData<Forecast>(repo.getForecast())
}