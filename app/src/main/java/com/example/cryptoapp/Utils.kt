package com.example.cryptoapp

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun convertStampToTime(timestamp: Long?): String {
    timestamp?.let {
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
    return ""
}