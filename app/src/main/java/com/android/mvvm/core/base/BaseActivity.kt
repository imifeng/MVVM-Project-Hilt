package com.android.mvvm.core.base

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil

/**
 * @author Finn
 * @date 2020
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutId
        if (view != 0) {
            setContentView(view)
        }
        StatusBarUtil.setTranslucentForImageView(this, 50, null)
        init()
    }

    abstract val layoutId: Int

    protected open fun init() {}

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
}