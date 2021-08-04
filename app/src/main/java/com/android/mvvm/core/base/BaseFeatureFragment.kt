package com.android.mvvm.core.base

import com.android.mvvm.core.model.BaseProperties
import com.android.mvvm.core.model.FragmentProperties
import com.android.mvvm.service.SharedPrefService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFeatureFragment : BaseFragment() {

    abstract val fragmentProperties: FragmentProperties

    override val baseProperties: BaseProperties by lazy { fragmentProperties }

    @Inject
    lateinit var sp: SharedPrefService

    override fun onHeaderBack() {
    }
}