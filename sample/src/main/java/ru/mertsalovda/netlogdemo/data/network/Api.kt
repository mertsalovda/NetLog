package ru.mertsalovda.netlogdemo.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import ru.mertsalovda.netlogdemo.domain.models.CountryDto

interface Api {

    @GET("all?fields=name;flag;currencies")
    suspend fun getAllCountries() : Response<List<CountryDto>>

    @POST("all?fields=name;flag;currencies")
    suspend fun postAllCountries() : Response<List<CountryDto>>

    @GET("all?fields=flag")
    suspend fun getAllFlags() : Response<List<CountryDto>>

    @POST("all?fields=currencies")
    suspend fun postAllCurrencies() : Response<List<CountryDto>>
}