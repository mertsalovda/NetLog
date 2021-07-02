package ru.mertsalovda.netlog.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(mask: String): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm")
    return simpleDateFormat.format(this)
}