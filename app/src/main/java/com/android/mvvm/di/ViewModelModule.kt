package com.android.mvvm.di

import com.android.mvvm.repository.RepoRepository
import com.android.mvvm.viewmodel.RepoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideRepoViewModel(repoRepository: RepoRepository) = RepoViewModel(repoRepository)
}