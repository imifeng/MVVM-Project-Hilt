package com.android.mvvm.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.android.mvvm.core.extension.tryCatchException
import com.android.mvvm.data.RepoBean
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.web.api.RepoApi
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val repoApi: RepoApi,
    private val repoDao: RepoBeanDao
) : RepoRepository {

    @WorkerThread
    override suspend fun loadRepos(username: String): List<RepoBean>? {
        try {
            val results = repoApi.getRepos(username)
            repoDao.insertAll(results)
            return results
        } catch (e: Exception) {
            e.tryCatchException()
        }
        return null
    }

    override fun getRepos(): LiveData<List<RepoBean>> {
        return repoDao.getAllRepos()
    }

}