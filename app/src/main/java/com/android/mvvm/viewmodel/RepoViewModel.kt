package com.android.mvvm.viewmodel

import androidx.lifecycle.*
import com.android.mvvm.data.RepoBean
import com.android.mvvm.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.android.mvvm.core.base.BaseViewModel
import com.android.mvvm.core.base.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : BaseViewModel() {

    sealed class RepoState : DataState {

        data class CheckSuccess(val checkLogin: Boolean) : RepoState()

        data class RepoDataState(val data: List<RepoBean>) : RepoState()
    }

    fun loadRepos(username: String) {
        repoRepository.loadRepos(username).onEach { dataState ->
            setDataState(dataState)
        }.launchIn(viewModelScope)
    }


    fun getRepos() {
        repoRepository.getRepos().onEach { dataState ->
            setDataState(dataState)
        }.launchIn(viewModelScope)
    }
}