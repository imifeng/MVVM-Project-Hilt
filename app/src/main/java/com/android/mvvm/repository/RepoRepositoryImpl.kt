package com.android.mvvm.repository

import com.android.mvvm.core.base.LoadingState
import com.android.mvvm.core.base.DataState
import com.android.mvvm.core.extension.catchFlowOnIO
import com.android.mvvm.core.extension.toDataStateWithoutLogin
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.viewmodel.RepoViewModel.RepoState.RepoDataState
import com.android.mvvm.web.api.RepoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RepoRepository {

    fun loadRepos(username: String): Flow<DataState>

    fun getRepos(): Flow<DataState>
}

class RepoRepositoryImpl @Inject constructor(
    private val repoApi: RepoApi,
    private val repoDao: RepoBeanDao
) : RepoRepository {

    override fun loadRepos(username: String): Flow<DataState> = flow {
        emit(LoadingState)
        val result = repoApi.getRepos(username)
        val state = result.toDataStateWithoutLogin {
            RepoDataState(it)
        }
        if (state is RepoDataState){
            repoDao.deleteAllRepos()
            repoDao.insertAll(state.data)
        }
        emit(state)
    }.catchFlowOnIO()

    override fun getRepos(): Flow<DataState> = flow {
        emit(LoadingState)
        val results = repoDao.getAllRepos()
        emit(RepoDataState(results))
    }.catchFlowOnIO()

}