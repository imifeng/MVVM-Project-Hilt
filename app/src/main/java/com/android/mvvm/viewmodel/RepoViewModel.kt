package com.android.mvvm.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.mvvm.data.RepoBean
import com.android.mvvm.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "RepoViewModel"
    }

    @WorkerThread
    suspend fun loadRepos(username: String): List<RepoBean>? {
        return repoRepository.loadRepos(username)
    }

    fun getRepos(): LiveData<List<RepoBean>> {
        return repoRepository.getRepos()
    }
}