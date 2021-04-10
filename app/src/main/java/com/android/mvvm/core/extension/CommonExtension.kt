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


//-50~0之间信号强dao度很好，使zhuan用感知好。
//-70~-50之间信号强度好。使用感知略差，但体验上无明显影响。
//-70以下 信号就不是太好了，使用上感知就不好。
fun Int.toRSSIValue(): String {
    return when {
        this > -50 -> {
            "好"
        }
        this <= -50 && this > -70 -> {
            "一般"
        }
        this < -70 -> {
            "差"
        }
        else -> {
            "差"
        }
    }
}


fun Float.toDecimalValue(format: String = "#0.00"): String {
    val df = DecimalFormat(format)
    return df.format(this)
}

//     1：差， 2：稍差 3：一般 4：好 5：很好
fun String.toLevelValue(): String {
    return when (this) {
        "0", "1" -> {
            "差"
        }
        "2" -> {
            "稍差"
        }
        "3" -> {
            "一般"
        }
        "4" -> {
            "好"
        }
        "5" -> {
            "很好"
        }
        else -> {
            "很好"
        }
    }
}

fun String.toWifiName(isSkipPermission: Boolean, subSequence: CharSequence): CharSequence {
    return if (isSkipPermission) {
        this
    } else {
        if (subSequence.contains("ssid")) {
            "未识别Wi-Fi"
        } else {
            subSequence
        }

    }
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