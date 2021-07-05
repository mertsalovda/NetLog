package ru.mertsalovda.netlog.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody


private const val TAG = "Netlog"

open class NetLogInterceptor(private val netLogRepository: INetLogRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val contentType: MediaType? = response.body()!!.contentType()
        val bodyString = response.body()?.string() ?: ""
        val body: ResponseBody = ResponseBody.create(contentType, bodyString)

            val responseForSaving = response.newBuilder()
                .body(body)
                .request(request)
                .build()

            netLogRepository.addItem(NetLogItem(request, responseForSaving, bodyString))

        Log.d(TAG, request.url().toString())

        return response.newBuilder().body(body).build()
    }
}