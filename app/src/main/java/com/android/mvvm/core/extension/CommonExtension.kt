package com.android.mvvm.core.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.android.mvvm.R
import java.net.UnknownHostException
import java.text.DecimalFormat
import kotlin.math.roundToInt

fun Exception.tryCatchException() {
    if (this is UnknownHostException){
//            val it = Intent(context, NoNetworkActivity::class.java)
//            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(it)
    }
    this.printStackTrace()
}

fun Int.toDp(): Float = this * Resources.getSystem().displayMetrics.density

fun Float.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).roundToInt()


fun Float.toDecimalValue(format: String = "#0.00"): String {
    val df = DecimalFormat(format)
    return df.format(this)
}


@SuppressLint("ResourceType")
fun Context.getForegroundColorSpan(
    span: String,
    change: String,
    @ColorInt color: Int = R.color.colorMain
): CharSequence? {
    val sb = SpannableStringBuilder(span)
    //改变部分文字的颜色
    val index = sb.indexOf(change)
    if (index > -1) {
        sb.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, color)),
            index,
            index + change.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
    return sb
}

fun Boolean.toDoActionForReView(callbackA: (() -> Unit)? = null, callbackB: (() -> Unit)? = null) {
    if (this) {
        callbackA?.invoke()
    } else {
        callbackB?.invoke()
    }
}

fun Int.toShortBoard(upperBound: Int = 999): String {
    return if (this in 1..upperBound) {
        this.toString()
    } else {
        "999+"
    }
}

fun Float.showShortValue(format: String = "#0.00", upperBound: Float = 99999F): String {
    return if (this > upperBound) {
        "" + (this / 10000).toDecimalValue(format) + "W"
    } else {
        this.toString()
    }
}

fun Int.showShortValue(format: String = "#0.00", upperBound: Int = 99999): String {
    return if (this > upperBound) {
        "" + (this / 10000F).toDecimalValue(format) + "W"
    } else {
        this.toString()
    }
}