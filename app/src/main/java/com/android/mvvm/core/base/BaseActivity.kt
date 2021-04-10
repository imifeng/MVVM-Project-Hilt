package com.android.mvvm.core.base

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.mvvm.R
import com.android.mvvm.core.constant.Constant
import com.android.mvvm.core.extension.*
import com.android.mvvm.service.SharedPrefService
import com.jaeger.library.StatusBarUtil
import com.android.mvvm.core.model.ActivityProperties
import com.android.mvvm.service.NetworkMonitor
import kotlinx.android.synthetic.main.layout_header.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/**
 * @author Finn
 * @date 2020
 */
abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    abstract val activityProperties: ActivityProperties

    /**
     * @suppress
     */
    override val kodein: Kodein by kodein()

    protected val sp: SharedPrefService by instance()
    protected val networkMonitor: NetworkMonitor by instance()

    /**
     * The logging tag to be used when debugging. Will use the inheritors simple name.
     */
    val TAG = this::class.java.simpleName

    // 大多用于避免由于使屏幕旋转导致意图被重新执行
    protected var savedState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isPad() && !isActivityTransparent()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 设置activity始终是竖屏,
        }

        if (isNavigationBarColorWhite()) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorWhite)
        }

        savedState = savedInstanceState
        setContentView(activityProperties.layoutResID)
        StatusBarUtil.setTranslucentForImageView(this, 50, null)
        initHeaderView()

        init()

    }

    protected open fun init() {}

    private fun initHeaderView() {
        view_status_bar?.adaptStatusBarHeight()
        if (activityProperties.showHeader == true) {
            layout_header?.show()
            activityProperties.title?.let {
                header_title.text = getText(it)
                header_title.show()
                header_title_logo?.hide()
            }
            if (activityProperties.showBack == true) {
                activityProperties.backDrawableRes?.let {
                    header_image_back.setImageResource(it)
                }
                header_image_back.show()
                header_image_back.setOnSingleClickListener {
                    onBackPressed()
                }
            }
            if (activityProperties.showAction == true) {
                activityProperties.actionDrawableRes?.let {
                    header_image_action.setImageResource(it)
                    header_image_action.show()
                }
            }
        } else {
            layout_header?.hide()
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