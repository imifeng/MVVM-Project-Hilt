package com.kotlin.project.extension

import android.content.res.Resources
import android.util.TypedValue

/**
 * Return the given int as a dpi unit
 *
 * @param value
 * @return the dpi value
 */
fun Resources.toDpi(value: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), displayMetrics).toInt()
}