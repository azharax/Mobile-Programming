package com.example.newsapp.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Converts an ISO-8601 timestamp from the API (e.g. "2024-01-15T12:34:56Z")
 * into a human readable relative string such as "3 hours ago".
 * Uses SimpleDateFormat so it works on minSdk 24 without core library desugaring.
 */
object DateUtils {

    fun timeAgo(publishedAt: String?): String {
        if (publishedAt.isNullOrBlank()) return ""
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = parser.parse(publishedAt) ?: return ""
            val diff = System.currentTimeMillis() - date.time
            val minutes = diff / 60_000
            val hours = minutes / 60
            val days = hours / 24
            when {
                minutes < 1 -> "just now"
                minutes < 60 -> "$minutes ${plural(minutes, "minute")} ago"
                hours < 24 -> "$hours ${plural(hours, "hour")} ago"
                else -> "$days ${plural(days, "day")} ago"
            }
        } catch (e: Exception) {
            ""
        }
    }

    private fun plural(value: Long, unit: String) = if (value == 1L) unit else "${unit}s"
}
