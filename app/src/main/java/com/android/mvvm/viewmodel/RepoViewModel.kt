package com.android.mvvm.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.mvvm.data.RepoBean
import com.android.mvvm.data.dao.RepoBeanDao
import com.android.mvvm.web.RepoApi

class RepoViewModel(
    private val repoDao: RepoBeanDao,
    private val repoApi: RepoApi,
) : ViewModel() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    @WorkerThread
    suspend fun loadRepos(username: String): List<RepoBean> {
        val results = repoApi.getRepos(username)
        repoDao.insertAll(results)
        return results
    }


    fun getRepos(): LiveData<List<RepoBean>> {
        return repoDao.getAllRepos()
    }
}