package com.android.mvvm.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged

/**
 * 使用 State 接口和状态成员变量对 View 进行 UI 更改
 */
interface DataState

object LoginState : DataState

object LoadingState : DataState

/**
 * The result of a non 2xx response to a network request.
 */
data class ServerErrorState(val code: Int, val message: String? = null) : DataState

/**
 * The result of a network connectivity error, or for example, json parsing error
 */
data class ErrorState(val message: String? = null) : DataState

/**
 * 例如，用于在导航后重置视图模型的状态
 */
object Reset : DataState


abstract class BaseViewModel : ViewModel() {
    /**
     * 建议观察状态 LiveData 并在 UI 上进行更改
     */
    private val _dataState = MutableLiveData<DataState>()
    val dataState: LiveData<DataState>
        get() = _dataState.distinctUntilChanged()

    protected fun setDataState(state: DataState) {
        _dataState.value = state
    }

    fun resetState() {
        _dataState.value = Reset
    }

    override fun onCleared() {
        resetState()
        super.onCleared()
    }
}