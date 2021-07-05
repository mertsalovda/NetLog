package ru.mertsalovda.netlogdemo.data.network

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mertsalovda.netlog.interceptor.INetLogRepository
import ru.mertsalovda.netlog.interceptor.NetLogInterceptor
import ru.mertsalovda.netlogdemo.App
import ru.mertsalovda.netlogdemo.BuildConfig

const val COUNTRIES_BASE_URL = "https://restcountries.eu/rest/v2/"

object CountriesApi {

    private var api: Api? = null
    private var gson: Gson? = null
    private var okHttpClient: OkHttpClient? = null
    private var gsonConverterFactory: GsonConverterFactory? = null
    private var httpLoggingInterceptor: HttpLoggingInterceptor? = null
    private var coroutineCallAdapterFactory: CoroutineCallAdapterFactory? = null

    fun provideGson(): Gson = gson ?: Gson().apply { gson = this }

    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = gsonConverterFactory ?:
        GsonConverterFactory.create(gson).apply { gsonConverterFactory = this }

    fun provideKotlinCoroutineAdapter() = coroutineCallAdapterFactory ?: CoroutineCallAdapterFactory()
        .apply { coroutineCallAdapterFactory = this }

    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return if (okHttpClient != null) {
            okHttpClient!!
        } else {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(interceptor)
            }
            builder.build().apply { okHttpClient = this }
        }
    }

    private fun provideNetLogRepository(): INetLogRepository = App.netLogRepository

    private fun provideNetLogInterceptor(repository: INetLogRepository) = NetLogInterceptor(repository)

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = httpLoggingInterceptor ?:
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY).apply { httpLoggingInterceptor = this }

    fun provideCountriesApi(): Api = api ?:
        Retrofit.Builder()
            .baseUrl(COUNTRIES_BASE_URL)
            .client(provideOkHttpClient(provideNetLogInterceptor(provideNetLogRepository())))
            .addConverterFactory(provideGsonConverterFactory(provideGson()))
            .addCallAdapterFactory(provideKotlinCoroutineAdapter())
            .build()
            .create(Api::class.java)
            .apply {
                api = this
            }
}