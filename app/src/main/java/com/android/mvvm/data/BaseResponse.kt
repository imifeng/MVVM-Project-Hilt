package com.android.mvvm.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    val code: Int,
    val message: String?,
    val data: T? = null
)