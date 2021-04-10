package com.android.mvvm.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.mvvm.core.extension.adaptStatusBarHeight
import com.android.mvvm.core.extension.hide
import com.android.mvvm.core.extension.show
import com.android.mvvm.core.model.FragmentProperties
import com.android.mvvm.service.SharedPrefService
import com.android.mvvm.util.Logger
import kotlinx.android.synthetic.main.layout_header.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


abstract class BaseFragment : Fragment(), KodeinAware {

    abstract val fragmentProperties: FragmentProperties

    /**
     * @suppress
     */
    override val kodein: Kodein by kodein()

    protected val sp: SharedPrefService by instance()

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
        initHeaderView()
        init()
    }

    /**
     * 初始化视图
     */
    protected open fun init() {}


    private fun initHeaderView() {
        view_status_bar?.adaptStatusBarHeight()
        if (fragmentProperties.showHeader == true) {
            layout_header?.show()
            fragmentProperties.title?.let {
                header_title.text = getText(it)
                header_title.show()
                header_title_logo.hide()
            }
            fragmentProperties.titleDrawableRes?.let {
                header_title_logo.setImageResource(it)
                header_title_logo.show()
                header_title.hide()
            }
            if (fragmentProperties.showBack == true) {
                fragmentProperties.backDrawableRes?.let {
                    header_image_back.setImageResource(it)
                }
                header_image_back.show()
            }
            if (fragmentProperties.showAction == true) {
                fragmentProperties.actionDrawableRes?.let {
                    header_image_action.setImageResource(it)
                    header_image_action.show()
                }
            }
        } else {
            layout_header?.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d(TAG, "OnDestroyView")
    }

}