package com.android.mvvm.core.base

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.android.mvvm.R
import com.android.mvvm.core.constant.Constant
import com.android.mvvm.core.extension.*
import com.android.mvvm.service.SharedPrefService
import com.jaeger.library.StatusBarUtil
import com.android.mvvm.core.model.BaseProperties
import com.android.mvvm.databinding.ActivityBaseBinding
import com.android.mvvm.databinding.ActivityMainBinding
import com.android.mvvm.service.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Finn
 * @date 2021
 */
@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {

    abstract val baseProperties: BaseProperties

    private val viewBinding: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }

//    private val viewBinding by viewBinding(ActivityBaseBinding::inflate)

    internal val viewParent: View by lazy {
        viewBinding.viewStub.inflate()
    }

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

        viewBinding.viewStub.layoutResource = baseProperties.layoutResource
        setContentView(viewBinding.root)
        StatusBarUtil.setTranslucentForImageView(this, 50, null)
        initHeaderView()

        init()
    }

    protected open fun init() {}

    private fun initHeaderView() {
        with(viewBinding){
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
                        onHeaderBack()
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

    open fun onHeaderBack() {}

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