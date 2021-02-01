package com.android.mvvm.di

import com.android.mvvm.App
import com.android.mvvm.BuildConfig
import com.android.mvvm.data.typeconverters.DateAdapters
import com.android.mvvm.data.typeconverters.BooleanAdapters
import com.android.mvvm.web.RepoApi
import com.android.mvvm.web.interceptor.HeadInterceptor
import com.android.mvvm.web.interceptor.WebLogger
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val webModule = Kodein.Module(name = "webModule") {

    bind<Moshi>() with singleton {
        Moshi.Builder()
            .add(BooleanAdapters())
            .add(DateAdapters())
            .build()
    }

    bind<MoshiConverterFactory>() with singleton {
        MoshiConverterFactory.create(instance())
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(HeadInterceptor(instance()))
            .addInterceptor(WebLogger(instance<App>()))
            .retryOnConnectionFailure(true)
            .build()
    }

    bind<Retrofit>(tag = "RepoApi") with singleton {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(instance())
            .client(instance())
            .build()
    }

    bind<RepoApi>() with singleton {
        instance<Retrofit>(tag = "RepoApi").create(RepoApi::class.java)
    }
}