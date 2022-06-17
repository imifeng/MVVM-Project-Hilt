package com.android.mvvm.core.extension

import com.android.mvvm.core.base.DataState
import com.android.mvvm.core.base.ErrorState
import com.android.mvvm.core.base.LoginState
import com.android.mvvm.core.base.ServerErrorState
import com.android.mvvm.web.BasicResponse
import com.android.mvvm.web.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

fun Flow<DataState>.catchFlowOnIO() = this.catch { e ->
    ErrorState(e.message)
}.flowOn(Dispatchers.IO)

fun <T : Any> BasicResponse<T>.toDataStateWithoutLogin(successCallback: ((body: T) -> DataState)) =
    when (this) {
        is NetworkResponse.Success -> {
            body.data?.let {
                successCallback.invoke(body.data)
            } ?: ServerErrorState(body.code, body.message)
        }
        is NetworkResponse.ApiError -> ServerErrorState(code, body.message)
        is NetworkResponse.NetworkError -> ErrorState(error.message)
        is NetworkResponse.UnknownError -> ErrorState(error?.message)
    }

fun <T : Any> BasicResponse<T>.toDataState(successCallback: ((body: T) -> DataState)) = when (this) {
    is NetworkResponse.Success -> {
        body.data?.let {
            successCallback.invoke(body.data)
        } ?: ServerErrorState(body.code, body.message)
    }
    is NetworkResponse.ApiError -> {
        if (code in arrayOf(401, 403) || body.message == "Unauthorized") {
            LoginState
        } else {
            ServerErrorState(code, body.message)
        }
    }
    is NetworkResponse.NetworkError -> ErrorState(error.message)
    is NetworkResponse.UnknownError -> ErrorState(error?.message)
}

/**
 * Allow checking if the input values are not null. If values are not null then the function block
 * is going to be executed
 *
 * @param T1 first value type
 * @param T2 second value type
 * @param R return value type
 * @param p1 first value
 * @param p2 second value
 * @param block function block to be executed if input values are not null
 * @return the value to be returned if any
 */
inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}


/**
 * Allow checking if the input values are not null. If values are not null then the function block
 * is going to be executed
 *
 * @param T1 first value type
 * @param T2 second value type
 * @param R return value type
 * @param p1 first value
 * @param p2 second value
 * @param block function block to be executed if input values are not null
 * @return the value to be returned if any
 */
inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}