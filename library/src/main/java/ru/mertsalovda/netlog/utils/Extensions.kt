package ru.mertsalovda.netlog.utils

import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


fun Date.format(mask: String): String {
    val simpleDateFormat = SimpleDateFormat(mask)
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
    val bodyString = body?.toString() ?: ""
    val formattedBody = try {
        if (bodyString.isNotEmpty()) JSONObject(bodyString).toString(4) else body
    } catch (e: Exception) {
        try {
            if (bodyString.isNotEmpty()) JSONArray(bodyString).toString(4) else body
        } catch (e: Exception) {
            ""
        }
    }
    stringBuilder.append(formattedBody ?: "")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append("\n")



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
    val formattedBody = try {
        if (body.isNotEmpty()) JSONObject(body).toString(4) else body
    } catch (e: Exception) {
        try {
            if (body.isNotEmpty()) JSONArray(body).toString(4) else body
        } catch (e: Exception) {
            ""
        }
    }

    stringBuilder.append("-- Body --")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append(formattedBody)
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append("\n")
    stringBuilder.append("\n")

    return stringBuilder.toString()
}