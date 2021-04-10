package com.android.mvvm.core.model

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

data class FragmentProperties(
    @LayoutRes val resource: Int,
    val showHeader: Boolean? = false,
    @StringRes val title: Int? = null,
    @DrawableRes val titleDrawableRes: Int? = null,
    val showBack: Boolean? = false,
    @DrawableRes val backDrawableRes: Int? = null,
    val showAction: Boolean? = false,
    @DrawableRes val actionDrawableRes: Int? = null
)