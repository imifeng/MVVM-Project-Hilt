package com.android.mvvm.core.model

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

data class BaseProperties(
    @LayoutRes val layoutResource: Int,
    val hasHeader: Boolean? = false,
    @StringRes val headerTitle: Int? = null,
    val hasBack: Boolean? = false,
    @DrawableRes val backDrawableRes: Int? = null,
    val hasAction: Boolean? = false,
    @DrawableRes val actionDrawableRes: Int? = null
)