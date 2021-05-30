package ru.mertsalovda.netlog.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mertsalovda.netlog.data.network.CountriesApi
import ru.mertsalovda.netlog.domain.models.CountryDto

class MainViewModel : ViewModel() {

    private val _countries = MutableLiveData<List<CountryDto>>()
    val countries: LiveData<List<CountryDto>> = _countries

    fun sendRest() {
        viewModelScope.launch {
            try {
                val allCountries = CountriesApi.provideCountriesApi().getAllCountries()
                _countries.value = allCountries.body()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}