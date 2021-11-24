package com.android.mvvm.viewmodel

import androidx.lifecycle.*
import com.android.mvvm.data.RepoBean
import com.android.mvvm.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import androidx.lifecycle.LiveData
import com.android.mvvm.core.base.BaseViewModel
import com.android.mvvm.core.base.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : BaseViewModel() {

    sealed class RepoState : State {

        data class CheckSuccess(val checkLogin: Boolean) : RepoState()

        data class RepoDataState(val data: List<RepoBean>) : RepoState()
    }

    fun loadRepos(username: String) {
        viewModelScope.launch {
            repoRepository.loadRepos(username).onEach { dataState ->
                setState(dataState)
            }.launchIn(viewModelScope)
        }
    }


    fun getRepos() {
        viewModelScope.launch {
            repoRepository.getRepos().onEach { dataState ->
                setState(dataState)
            }.launchIn(viewModelScope)
        }
    }
}