package ru.mertsalovda.netlog.model

import okhttp3.Request
import okhttp3.Response

/**
 * Network log view
 */
data class NetLogItem(
    val request: Request,
    val response: Response,
    val responseBody: String
    )