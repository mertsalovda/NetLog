package ru.mertsalovda.netlogdemo.data.network

import retrofit2.Response
import retrofit2.http.*
import ru.mertsalovda.netlogdemo.domain.models.CountryDto

interface Api {

    @GET("all?fields=name;flag;currencies")
    suspend fun getAllCountries() : Response<List<CountryDto>>

    @Headers("JSESSION: DKJH1564365416DFK", "ANY: PARAMS")
    @POST("all?fields=name;flag;currencies")
    suspend fun postAllCountries(@Body body: String = "{\"text\":\"string\"}") : Response<List<CountryDto>>

    @Headers("JSESSION: DKJH1564365416DFK", "ANY: PARAMS")
    @GET("all?fields=flag")
    suspend fun getAllFlags() : Response<List<CountryDto>>

    @POST("all?fields=currencies")
    suspend fun postAllCurrencies() : Response<List<CountryDto>>
}