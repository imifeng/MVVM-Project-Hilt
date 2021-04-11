package com.android.mvvm.di

import com.android.mvvm.repository.RepoRepository
import com.android.mvvm.repository.RepoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * TasksRepository binding to use in tests.
 *
 * Hilt will inject a [FakeRepository] instead of a [DefaultTasksRepository].
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: RepoRepositoryImpl): RepoRepository

}