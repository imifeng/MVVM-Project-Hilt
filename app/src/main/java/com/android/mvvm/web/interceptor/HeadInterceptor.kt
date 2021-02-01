package com.android.mvvm.web.interceptor

import com.android.mvvm.service.SharedPrefService
import okhttp3.Interceptor
import okhttp3.Response

class HeadInterceptor(val sp: SharedPrefService) : Interceptor {

    companion object{
        const val HEADER_TOKEN = "Authorization"
        const val HEADER_VERSION = "app_version"
        const val HEADER_PLATFORM = "platform"
        const val PLATFORM_ANDROID = "android"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .addHeader(HEADER_TOKEN, sp.toString())
            .addHeader(HEADER_VERSION, sp.version.toString())
            .addHeader(HEADER_PLATFORM, PLATFORM_ANDROID)
            .build()
        return chain.proceed(request)
    }
}