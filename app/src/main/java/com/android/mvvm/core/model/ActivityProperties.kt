package com.android.mvvm.core.model

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

data class ActivityProperties(
    @LayoutRes override val resource: Int,
    override val hasHeader: Boolean = true,
    override val hasBack: Boolean = true,
    @DrawableRes override val backDrawableRes: Int? = null,
    @StringRes override val headerTitle: Int? = null,
    @DrawableRes override val headerTitleDrawableRes: Int? = null,
    override val hasAction: Boolean = false,
    @LayoutRes override val actionResource: Int? = null,

    val showConnectivityIcons: Boolean = false,
) : BaseProperties()