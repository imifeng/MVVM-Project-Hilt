package com.android.mvvm.core.extension

import android.app.Activity
import android.util.TypedValue
import com.android.mvvm.R

/**
 * 当前Activity是否设置透明背景
 */
fun Activity.isActivityTransparent(): Boolean {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(R.style.TranslucentTheme, typedValue, true)
    val attribute = intArrayOf(android.R.attr.windowIsTranslucent)
    val array = obtainStyledAttributes(typedValue.resourceId, attribute)
    val isTransparent = array.getBoolean(0, false)
    array.recycle()
    return isTransparent
}