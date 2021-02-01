package com.android.mvvm.core.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.*
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.toLocalDateTime(pattern: String = "M/dd/yyyy h:mm a"): String = LocalDateTime
    .ofInstant(this, ZoneId.systemDefault())
    .format(DateTimeFormatter.ofPattern(pattern))

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.toLocalDate(pattern: String = "M/dd/yyyy"): String = LocalDateTime
    .ofInstant(this, ZoneId.systemDefault())
    .format(DateTimeFormatter.ofPattern(pattern))

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.toLocalTime(pattern: String = "h:mm a"): String = LocalDateTime
    .ofInstant(this, ZoneId.systemDefault())
    .format(DateTimeFormatter.ofPattern(pattern))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toLocalDateString(pattern: String = "M-dd-yyyy"): String = this.format(
    DateTimeFormatter.ofPattern(pattern)
)

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toLocalTimeString(pattern: String = "h:mm a"): String = this.format(
    DateTimeFormatter.ofPattern(pattern)
)

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toLocalDateString(pattern: String = "M/dd/yyyy"): String = this.format(
    DateTimeFormatter.ofPattern(pattern)
)

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.getStartOfDay(pattern: String = "yyyy-MM-dd"): LocalDateTime =
    LocalDate.parse(this.format(DateTimeFormatter.ofPattern(pattern))).atStartOfDay()
