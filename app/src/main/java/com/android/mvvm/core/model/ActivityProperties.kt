package com.android.mvvm.core.model

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

data class ActivityProperties(
    @LayoutRes val layoutResID: Int,
    val showHeader: Boolean? = false,
    @StringRes val title: Int? = null,
    val showBack: Boolean? = false,
    @DrawableRes val backDrawableRes: Int? = null,
    val showAction: Boolean? = false,
    @DrawableRes val actionDrawableRes: Int? = null
)