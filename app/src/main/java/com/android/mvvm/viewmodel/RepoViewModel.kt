package com.android.mvvm.viewmodel

import androidx.lifecycle.*
import com.android.mvvm.data.RepoBean
import com.android.mvvm.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import androidx.lifecycle.LiveData
import com.android.mvvm.core.model.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "RepoViewModel"
    }

    private val _loadRepoDataState: MutableLiveData<DataState<List<RepoBean>>> by lazy { MutableLiveData() }
    val loadRepoDataState: LiveData<DataState<List<RepoBean>>>
        get() = _loadRepoDataState

    fun loadRepos(username: String) {
        viewModelScope.launch {
            repoRepository.loadRepos(username).onEach { dataState ->
                _loadRepoDataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }


    private val _getReposDataState: MutableLiveData<DataState<List<RepoBean>>> by lazy { MutableLiveData() }
    val getReposDataState: LiveData<DataState<List<RepoBean>>>
        get() = _getReposDataState

    fun getRepos() {
        viewModelScope.launch {
            repoRepository.getRepos().onEach { dataState ->
                _getReposDataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}