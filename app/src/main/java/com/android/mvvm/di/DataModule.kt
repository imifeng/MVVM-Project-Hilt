package com.android.mvvm.di

import android.content.Context
import com.android.mvvm.data.AppDatabase
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.repository.RepoRepository
import com.android.mvvm.repository.RepoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.initialize(appContext)
    }

    @Provides
    fun provideRepoDao(database: AppDatabase): RepoBeanDao {
        return database.repoDao()
    }
}