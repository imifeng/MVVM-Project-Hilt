package com.android.mvvm.ui.test

import androidx.fragment.app.Fragment
import com.android.mvvm.R
import com.android.mvvm.core.base.BaseFragment
import com.android.mvvm.core.model.FragmentProperties

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TestFragment : BaseFragment() {
    override val fragmentProperties = FragmentProperties(resource = R.layout.fragment_test)

    override fun init() {
        super.init()
    }

}