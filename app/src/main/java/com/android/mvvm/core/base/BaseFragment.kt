package com.android.mvvm.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.mvvm.core.model.FragmentProperties
import com.android.mvvm.util.Logger
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein


abstract class BaseFragment : Fragment(), KodeinAware {

    abstract val fragmentProperties: FragmentProperties

    /**
     * @suppress
     */
    override val kodein: Kodein by kodein()

    /**
     * The logging tag to be used when debugging. Will use the inheritors simple name.
     */
    val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d(TAG, "OnCreateView")
        return inflater.inflate(fragmentProperties.resource, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(TAG, "OnViewCreated")
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d(TAG, "OnDestroyView")
    }

    /**
     * 初始化视图
     */
    protected open fun init() {}

}