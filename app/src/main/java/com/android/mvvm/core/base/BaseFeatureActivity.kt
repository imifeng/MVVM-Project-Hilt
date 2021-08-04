package com.android.mvvm.core.base

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.android.mvvm.R
import com.android.mvvm.core.constant.Constant
import com.android.mvvm.core.extension.*
import com.android.mvvm.service.SharedPrefService
import com.android.mvvm.core.model.ActivityProperties
import com.android.mvvm.core.model.BaseProperties
import com.android.mvvm.service.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Finn
 * @date 2021
 */
@AndroidEntryPoint
abstract class BaseFeatureActivity : BaseActivity() {

    abstract val activityProperties: ActivityProperties

    override val baseProperties: BaseProperties by lazy { activityProperties }

    @Inject lateinit var sp: SharedPrefService
    @Inject lateinit var networkMonitor: NetworkMonitor

    /**
     * The logging tag to be used when debugging. Will use the inheritors simple name.
     */
    val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isPad() && !isActivityTransparent()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 设置activity始终是竖屏,
        }

        if (isNavigationBarColorWhite()) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorWhite)
        }
    }

    open fun handleBack() = false

    override fun onBackPressed() {
        if (!handleBack()) {
            super.onBackPressed()
        }
    }

    /**
     * Set font size, language
     * @return
     */
    override fun getResources(): Resources {
        //Set font size, language
        return super.getResources()
    }

    fun isPad(): Boolean {
        return (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    /**
     * 当前Activity是否需要设置底部虚拟键背景色
     */
    fun isNavigationBarColorWhite(): Boolean {
        return Constant.NAVIGATION_BAR_ACTIVITY_LIST.any {
            this::class.java.simpleName == it
        }
    }
}