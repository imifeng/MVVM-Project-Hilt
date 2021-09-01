package com.android.mvvm.di

import android.content.Context
import com.android.mvvm.BuildConfig
import com.android.mvvm.data.typeconverters.DateAdapters
import com.android.mvvm.data.typeconverters.BooleanAdapters
import com.android.mvvm.web.api.RepoApi
import com.android.mvvm.web.interceptor.HeadInterceptor
import com.android.mvvm.web.interceptor.WebLogger
import com.android.mvvm.service.NetworkMonitor
import com.android.mvvm.service.SharedPrefService
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class CommonOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class QuickOkHttpClient

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(BooleanAdapters())
            .add(DateAdapters())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideWebLogger(@ApplicationContext appContext: Context): WebLogger {
        return WebLogger(appContext)
    }

    @Provides
    @Singleton
    fun provideHeadInterceptor(sp: SharedPrefService): HeadInterceptor {
        return HeadInterceptor(sp)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headInterceptor: HeadInterceptor,
        webLogger: WebLogger
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(headInterceptor)
            .addInterceptor(webLogger)
            .retryOnConnectionFailure(true)
            .build()
    }

    @QuickOkHttpClient
    @Provides
    @Singleton
    fun provideQuickOkHttpClient(
        timeout: Long,
        headInterceptor: HeadInterceptor,
        webLogger: WebLogger
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(timeout, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(timeout, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(timeout, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(headInterceptor)
            .addInterceptor(webLogger)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, factory: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRepoApi(retrofit: Retrofit): RepoApi {
        return retrofit.create(RepoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context,
        client: OkHttpClient
    ): NetworkMonitor {
        return NetworkMonitor(context, client)
    }
}