package com.android.mvvm.repository

import com.android.mvvm.core.model.DataState
import com.android.mvvm.data.RepoBean
import kotlinx.coroutines.flow.Flow

interface RepoRepository {

    suspend fun loadRepos(username: String): Flow<DataState<List<RepoBean>>>

    suspend fun getRepos(): Flow<DataState<List<RepoBean>>>
}