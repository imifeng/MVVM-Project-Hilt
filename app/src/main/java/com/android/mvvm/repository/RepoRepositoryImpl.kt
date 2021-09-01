package com.android.mvvm.repository

import com.android.mvvm.core.extension.tryCatchException
import com.android.mvvm.core.model.DataState
import com.android.mvvm.data.RepoBean
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.web.api.RepoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val repoApi: RepoApi,
    private val repoDao: RepoBeanDao
) : RepoRepository {

    override suspend fun loadRepos(username: String): Flow<DataState<List<RepoBean>>> = flow {
        try {
            emit(DataState.Loading)
            val results = repoApi.getRepos(username)
            repoDao.deleteAllRepos()
            repoDao.insertAll(results)
            emit(DataState.Success(results))
        } catch (e: Exception) {
            e.tryCatchException()
            emit(DataState.Error(e))
        }
    }


    override suspend fun getRepos(): Flow<DataState<List<RepoBean>>> = flow {
        try {
            emit(DataState.Loading)
            val results = repoDao.getAllRepos()
            emit(DataState.Success(results))
        } catch (e: Exception) {
            e.tryCatchException()
            emit(DataState.Error(e))
        }
    }

}