package com.android.mvvm.web

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorBody(
    val code: Int,
    val message: String?,
    val error: String?
)