package com.android.mvvm.core.extension

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * @function 获得界面宽
 */
fun Activity.getPhoneWidth(): Int {
    val metric = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(metric)
    return metric.widthPixels
}

fun Activity.getPhoneWidthWithDp(): Float {
    val metric = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(metric)
    return (metric.widthPixels / metric.density + 0.5f)
}

/**
 * @function 获得界面高
 */
fun Activity.getPhoneHeight(): Int {
    val metric = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(metric)
    return metric.heightPixels
}

fun Activity.getPhoneHeightWithDp(): Float {
    val metric = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(metric)
    return (metric.heightPixels / metric.density + 0.5f)
}

/**
 * 将传进来的数转化为dp
 */
fun Context.convertToDp(num: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        num.toFloat(),
        this.resources.displayMetrics
    ).toInt()
}

/**
 * 将传进来的数转化为sp
 */
fun Context.convertToSp(num: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        num.toFloat(),
        this.resources.displayMetrics
    ).toInt()
}