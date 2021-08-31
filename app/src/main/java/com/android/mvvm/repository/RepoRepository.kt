package com.android.mvvm.repository

import androidx.lifecycle.LiveData
import com.android.mvvm.data.RepoBean

interface RepoRepository {

    suspend fun loadRepos(username: String): LiveData<List<RepoBean>>?

    fun getRepos(): LiveData<List<RepoBean>>
}