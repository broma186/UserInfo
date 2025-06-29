package com.example.userInfo

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.Date
import java.util.Locale

object DateParser {

    fun relativeTimeString(createdOnMillis: Long): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val now = Instant.now()
            val then = Instant.ofEpochMilli(createdOnMillis)
            val diff = Duration.between(then, now).toMillis()
            calculateDateString(diff, createdOnMillis)
        } else {
            val now = System.currentTimeMillis()
            val diff = now - createdOnMillis
            calculateDateString(diff, createdOnMillis)
        }
    }

    private fun calculateDateString(diff: Long, createdOnMillis: Long): String {
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "Just now"
            seconds.toInt() == 60 -> "A minute ago"
            minutes < 60 -> "$minutes minutes ago"
            minutes.toInt() == 60 -> "An hour ago"
            hours < 24 -> "$hours hours ago"
            hours < 48 -> "Yesterday"
            days < 7 -> "$days days ago"
            else -> {
                SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date(createdOnMillis))
            }
        }
    }
}