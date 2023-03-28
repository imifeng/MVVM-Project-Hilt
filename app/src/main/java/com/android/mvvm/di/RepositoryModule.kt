package com.android.mvvm.di

import com.android.mvvm.repository.RepoRepository
import com.android.mvvm.repository.RepoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * RepoRepository binding to use in tests.
 *
 * Hilt will inject a [RepoRepositoryImpl] instead of a [RepoRepository].
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: RepoRepositoryImpl): RepoRepository

}