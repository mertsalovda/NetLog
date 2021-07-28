package ru.mertsalovda.netlogdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.mertsalovda.netlogdemo.data.network.CountriesApi
import ru.mertsalovda.netlogdemo.domain.models.CountryDto

class MainViewModel : ViewModel() {

    private val testRestList = listOf<suspend () -> Response<List<CountryDto>>>(
        {
            CountriesApi.provideCountriesApi().getAllCountries()
        },
        {
            CountriesApi.provideCountriesApi().postAllCountries()
        },
        {
            CountriesApi.provideCountriesApi().postLogin()
        },
        {
            CountriesApi.provideCountriesApi().postLoginPing()
        },
        {
            CountriesApi.provideCountriesApi().getAllFlags()
        },
        {
            CountriesApi.provideCountriesApi().postAllCurrencies()
        },
    )

    fun sendRest() {
        viewModelScope.launch {
            try {
                delay(2000)
                testRestList.random().invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}