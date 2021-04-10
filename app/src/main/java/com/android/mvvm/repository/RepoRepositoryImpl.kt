package com.android.mvvm.repository

import androidx.lifecycle.LiveData
import com.android.mvvm.core.extension.tryCatchException
import com.android.mvvm.data.RepoBean
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.web.api.RepoApi

class RepoRepositoryImpl(
        private val repoApi: RepoApi,
        private val repoDao: RepoBeanDao
) : RepoRepository {

    override suspend fun loadRepos(username: String): List<RepoBean>? {
        try {
            val results = repoApi.getRepos(username)
            if (results != null) {
                repoDao.insertAll(results)
                return results
            }
        } catch (e: Exception) {
            e.tryCatchException()
        }
        return null
    }

    override fun getRepos(): LiveData<List<RepoBean>> {
        return repoDao.getAllRepos()
    }

}