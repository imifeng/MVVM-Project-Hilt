package com.android.mvvm.repository

import com.android.mvvm.core.base.ErrorState
import com.android.mvvm.core.base.FailureState
import com.android.mvvm.core.base.LoadingState
import com.android.mvvm.core.base.State
import com.android.mvvm.core.model.*
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.viewmodel.RepoViewModel.RepoState.CheckSuccess
import com.android.mvvm.viewmodel.RepoViewModel.RepoState.RepoDataState
import com.android.mvvm.web.api.RepoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoRepository {

    fun loadRepos(username: String): Flow<State>

    fun getRepos(): Flow<State>
}

class RepoRepositoryImpl @Inject constructor(
    private val repoApi: RepoApi,
    private val repoDao: RepoBeanDao
) : RepoRepository {

    override fun loadRepos(username: String): Flow<State> = flow {
        emit(LoadingState)
        val results = repoApi.getRepos(username)
        if(results.isNotEmpty()){
            repoDao.deleteAllRepos()
            repoDao.insertAll(results)
            emit(CheckSuccess(true))
        }else{
            emit(FailureState(-1,"该用户还没上传 Repo"))
        }
    }.catch { e ->
        emit(ErrorState(e))
    }.flowOn(Dispatchers.IO)


    override fun getRepos(): Flow<State> = flow {
        emit(LoadingState)
        val results = repoDao.getAllRepos()
        emit(RepoDataState(results))
    }.catch { e ->
        emit(ErrorState(e))
    }.flowOn(Dispatchers.IO)

}