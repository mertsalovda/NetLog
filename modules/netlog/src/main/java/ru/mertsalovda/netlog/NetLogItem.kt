package ru.mertsalovda.netlog

import okhttp3.Request
import okhttp3.Response

data class NetLogItem(
    val request: Request,
    val response: Response,
    )