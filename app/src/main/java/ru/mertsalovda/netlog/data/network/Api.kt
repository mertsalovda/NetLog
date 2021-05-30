package ru.mertsalovda.netlog.data.network

import retrofit2.Response
import retrofit2.http.GET
import ru.mertsalovda.netlog.domain.models.CountryDto

interface Api {

    @GET("all?fields=name;flag;currencies")
    suspend fun getAllCountries() : Response<List<CountryDto>>
}