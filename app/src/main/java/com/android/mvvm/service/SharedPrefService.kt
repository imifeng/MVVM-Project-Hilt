package com.android.mvvm.service

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Base64
import androidx.core.content.edit
import com.android.mvvm.BuildConfig

class SharedPrefService(app: Application) {

    companion object {
        private const val TAG = "SharedPrefService"

        private const val SP_PREFIX = "${BuildConfig.APPLICATION_ID}.SHARED_PREF"
        private const val SHARED_PREF = "${SP_PREFIX}_${BuildConfig.BUILD_TYPE}"

        private const val USER = "$SP_PREFIX.USER"
        private const val USER_NAME = "$SP_PREFIX.USER_NAME"
        private const val USER_TOKEN = "$SP_PREFIX.USER_TOKEN"
    }

    private val sp: SharedPreferences

    init {
        sp = app.getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    val version: Int = BuildConfig.VERSION_CODE
    val versionName: String = BuildConfig.VERSION_NAME

    val deviceSerialNumber: String
        @SuppressLint("MissingPermission", "BuildSerial", "NewApi")
        get() = try {
            Build.getSerial()
        } catch (e: SecurityException) {
            "NO_PERMISSION"
        }

    /**
     * 检索用户名字
     */
    var userName: String
        get() = sp.getString(USER_NAME, "") ?: ""
        set(value) {
            sp.edit {
                putString(USER_NAME, value)
            }
        }

    /**
     * 检索用户Token
     */
    var userToken: String
        get() = sp.getString(USER_TOKEN, "") ?: ""
        set(value) {
            sp.edit {
                putString(USER_TOKEN, value)
            }
        }

    override fun toString(): String {
        val sb = StringBuilder()
            .append("{\"identifier\":\"mvvm\",")
            .append("\"token\":\"${userToken}\"}")
        return Base64.encodeToString(sb.toString().toByteArray(), Base64.NO_WRAP)
    }
}