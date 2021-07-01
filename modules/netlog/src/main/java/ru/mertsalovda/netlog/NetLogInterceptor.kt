package ru.mertsalovda.netlog

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG = "Netlog"

open class NetLogInterceptor(private val netLogRepository: INetLogRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        netLogRepository.addItem(NetLogItem(request, response))

        Log.d(TAG, request.url.toString())
        return response
    }
}