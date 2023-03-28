package com.android.mvvm.core.base

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.mvvm.R
import com.android.mvvm.core.constant.Constant
import com.android.mvvm.core.extension.*
import com.jaeger.library.StatusBarUtil
import com.android.mvvm.core.model.BaseProperties
import com.android.mvvm.databinding.ActivityBaseBinding

/**
 * @author Finn
 * @date 2021
 */

abstract class BaseActivity : AppCompatActivity() {

    abstract val baseProperties: BaseProperties

    private val viewBinding: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }

    internal val viewParent: View by lazy {
        viewBinding.viewStub.inflate()
    }

    companion object {
        /**
         * The logging tag to be used when debugging. Will use the inheritors simple name.
         */
        val TAG: String = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isPad() && !isActivityTransparent()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED // 设置activity始终是竖屏,
        }

        if (isNavigationBarColorWhite()) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorWhite)
        }

        viewBinding.viewStub.layoutResource = baseProperties.layoutResource
        setContentView(viewBinding.root)
        StatusBarUtil.setTranslucentForImageView(this, 50, null)
        initHeaderView()

        init()
    }

    protected open fun init() {}

    private fun initHeaderView() {
        with(viewBinding) {
            viewStatusBar.adaptStatusBarHeight()
            if (baseProperties.hasHeader == true) {
                layoutHeader.show()
                baseProperties.headerTitle?.let {
                    headerTitle.text = getText(it)
                    headerTitle.show()
                }
                if (baseProperties.hasBack == true) {
                    baseProperties.backDrawableRes?.let {
                        headerBack.setImageResource(it)
                    }
                    headerBack.show()
                    headerBack.setOnSingleClickListener {
                        onHeaderBackClick(it)
                    }
                }
                if (baseProperties.hasAction == true) {
                    baseProperties.actionDrawableRes?.let {
                        headerAction.setImageResource(it)
                        headerAction.show()
                    }
                }
            } else {
                layoutHeader.hide()
            }
        }
    }

    open fun onHeaderBackClick(view: View) {}

    open fun handleBack() = false

    override fun onBackPressed() {
        if (!handleBack()) {
            super.onBackPressed()
        }
    }

    private fun isPad(): Boolean {
        return (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    /**
     * 当前Activity是否需要设置底部虚拟键背景色
     */
    private fun isNavigationBarColorWhite(): Boolean {
        return Constant.NAVIGATION_BAR_ACTIVITY_LIST.any {
            this::class.java.simpleName == it
        }
    }
}