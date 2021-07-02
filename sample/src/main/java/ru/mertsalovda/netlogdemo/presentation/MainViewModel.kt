package ru.mertsalovda.netlogdemo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.mertsalovda.netlogdemo.data.network.CountriesApi
import ru.mertsalovda.netlogdemo.domain.models.CountryDto

class MainViewModel : ViewModel() {

    private val _countries = MutableLiveData<List<CountryDto>>()
    val countries: LiveData<List<CountryDto>> = _countries

    private val testRestList = listOf<suspend () -> Response<List<CountryDto>>>(
        {
            CountriesApi.provideCountriesApi().getAllCountries()
        },
        {
            CountriesApi.provideCountriesApi().postAllCountries()
        },
        {
            CountriesApi.provideCountriesApi().getAllFlags()
        },
        {
            CountriesApi.provideCountriesApi().postAllCurrencies()
        },
    )

    fun sendRest() {
        _countries.value = listOf()
        viewModelScope.launch {
            try {
                delay(2000)
                val allCountries = testRestList.random().invoke()
                _countries.value = allCountries.body()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}