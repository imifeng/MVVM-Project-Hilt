package com.android.mvvm.util

import android.annotation.SuppressLint
import android.util.Log
import com.android.mvvm.BuildConfig

@SuppressLint("LogCatUse")
object Logger {

    private val DEBUG: Boolean = BuildConfig.DEBUG
    private const val MAX_LOG_SIZE = 3000

    fun v(tag: String, msg: String) {
        if (DEBUG) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String, tr: Throwable? = null) {
        if (DEBUG) {
            val logs = msg.chunked(MAX_LOG_SIZE)
            logs.forEach {
                Log.d(tag, it, tr)
            }
        }
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String, tr: Throwable? = null) {
        if (DEBUG) {
            Log.w(tag, msg, tr)
        }
    }

    fun e(tag: String, msg: String, tr: Throwable? = null) {
        if (DEBUG) {
            Log.e(tag, msg)
        }
    }

}