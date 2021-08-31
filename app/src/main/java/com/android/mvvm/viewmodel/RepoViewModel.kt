package com.android.mvvm.viewmodel

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.android.mvvm.data.RepoBean
import com.android.mvvm.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import androidx.lifecycle.Transformations

import androidx.lifecycle.LiveData




@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "RepoViewModel"
    }

    // Create a LiveData with a String
    val loadRepoLiveData: MutableLiveData<List<RepoBean>> by lazy {
        MutableLiveData<List<RepoBean>>().apply { value = emptyList() }
    }

    fun loadRepos(username: String){
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { repoRepository.loadRepos(username) }
            result?.let {
                loadRepoLiveData.postValue(it.value)
            }
        }
    }

    fun getRepos(): LiveData<List<RepoBean>> {
        return Transformations.distinctUntilChanged(repoRepository.getRepos())
    }
}