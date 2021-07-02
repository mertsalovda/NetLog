package ru.mertsalovda.netlog.utils

import okhttp3.Request
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(mask: String): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm")
    return simpleDateFormat.format(this)
}

fun Request.formatToString(): String {
    val stringBuilder = StringBuilder()

    val headerMap = this.headers.toMultimap()

    stringBuilder.append("-- Headers --")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    for ((key, value) in headerMap) {
        stringBuilder.append(key)
        stringBuilder.append("\n")
        stringBuilder.append(value.toString())
        stringBuilder.append("\n")
        stringBuilder.append("\n")
    }

    stringBuilder.append("-- Body --")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    body?.let { stringBuilder.append(it.toString()) }



    return stringBuilder.toString()
}

fun Response.formatToString(body: String): String {
    val stringBuilder = StringBuilder()

    val headerMap = this.headers.toMultimap()

    stringBuilder.append("-- Headers --")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    for ((key, value) in headerMap) {
        stringBuilder.append(key)
        stringBuilder.append("\n")
        stringBuilder.append(value.toString())
        stringBuilder.append("\n")
        stringBuilder.append("\n")
    }

    stringBuilder.append("-- Body --")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append(body)
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append("\n")



    return stringBuilder.toString()
}