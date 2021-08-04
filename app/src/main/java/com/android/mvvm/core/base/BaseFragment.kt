package com.android.mvvm.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.android.mvvm.R
import com.android.mvvm.core.extension.getLayoutInflater
import com.android.mvvm.core.extension.hide
import com.android.mvvm.core.extension.setOnSingleClickListener
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.model.BaseProperties
import kotlinx.android.synthetic.main.layout_base.*
import kotlinx.android.synthetic.main.layout_base.view.*
import kotlinx.android.synthetic.main.layout_header.*

abstract class BaseFragment : Fragment() {

    abstract val baseProperties: BaseProperties

    /**
     * The logging tag to be used when debugging. Will use the inheritors simple name.
     */
    val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = (inflater.inflate(R.layout.layout_base, null, false) as ConstraintLayout)
        inflater.inflate(baseProperties.resource, rootView.layout_content, true)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeaderView()
        initLayout()
    }

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
                    context?.getLayoutInflater()?.inflate(it, action_content, true)
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

    /**
     * Abstract class to be overridden by the inheritor. Used for running routines during the
     * [onViewCreated] method. This happens after the view has been initialized.
     */
    open fun initLayout() {}

    open fun onHeaderBack() {}
}