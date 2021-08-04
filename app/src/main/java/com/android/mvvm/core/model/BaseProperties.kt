package com.android.mvvm.core.model

abstract class BaseProperties {
    abstract val resource: Int
    abstract val hasHeader: Boolean
    abstract val hasBack: Boolean
    abstract val backDrawableRes: Int?
    abstract val headerTitle: Int?
    abstract val headerTitleDrawableRes: Int?
    abstract val hasAction: Boolean
    abstract val actionResource: Int?
}