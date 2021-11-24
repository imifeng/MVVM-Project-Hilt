package com.android.mvvm.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged

/**
 * 使用 State 接口和状态成员变量对 View 进行 UI 更改
 */
interface State

data class FailureState(val code: Int, val message: String) : State
data class ErrorState(val cause: Throwable) : State
object LoadingState : State

/**
 * 例如，用于在导航后重置视图模型的状态
 */
object Reset : State


abstract class BaseViewModel : ViewModel() {
    /**
     * 建议观察状态 LiveData 并在 UI 上进行更改
     */
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state.distinctUntilChanged()

    protected fun setState(state: State) {
        _state.postValue(state)
    }

    fun resetState() {
        _state.postValue(Reset)
    }

    override fun onCleared() {
        resetState()
        super.onCleared()
    }
}