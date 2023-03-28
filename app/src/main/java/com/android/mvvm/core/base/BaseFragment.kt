package com.android.mvvm.core.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.android.mvvm.util.Logger

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    companion object {
        /**
         * The logging tag to be used when debugging. Will use the inheritors simple name.
         */
        val TAG: String = this::class.java.simpleName

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(TAG, "OnViewCreated")
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d(TAG, "onAttach")
    }

    /**
     * 初始化视图
     */
    protected open fun init() {}
}