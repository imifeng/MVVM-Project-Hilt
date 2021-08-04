package com.android.mvvm.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mvvm.R
import com.android.mvvm.core.extension.*
import com.jaeger.library.StatusBarUtil
import com.android.mvvm.core.model.BaseProperties
import kotlinx.android.synthetic.main.layout_base.*
import kotlinx.android.synthetic.main.layout_header.*

/**
 * @author Finn
 * @date 2021
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val baseProperties: BaseProperties

    // 大多用于避免由于使屏幕旋转导致意图被重新执行
    protected var savedState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedState = savedInstanceState
        setContentView(R.layout.activity_base)
        this.layoutInflater.inflate(baseProperties.resource, layout_content, true)
        StatusBarUtil.setTranslucentForImageView(this, 50, null)
        initHeaderView()

        initLayout()
    }

    open fun initLayout() {}

    private fun initHeaderView() {
        if (baseProperties.hasHeader) {
            layout_header?.show()
            baseProperties.headerTitle?.let {
                header_title.text = getString(it)
                header_title.show()
                header_title_logo.hide()
            }
            baseProperties.headerTitleDrawableRes?.let {
                header_title_logo.setImageResource(it)
                header_title_logo.show()
                header_title.hide()
            }
            if (baseProperties.hasBack) {
                baseProperties.backDrawableRes?.let {
                    header_back.setImageResource(it)
                }
                header_back.show()
                header_back.setOnSingleClickListener {
                    onHeaderBack()
                }
            }
            if (baseProperties.hasAction) {
                baseProperties.actionResource?.let {
                    layoutInflater.inflate(it, action_content, true)
                }
                action_content.show()
            }
        } else {
            layout_header?.hide()
        }
    }

    fun setHeaderTitle(title: String) {
        header_title.text = title
        header_title.show()
    }

    open fun onHeaderBack() {}
}