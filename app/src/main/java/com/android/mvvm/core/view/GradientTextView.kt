package com.android.mvvm.core.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.android.mvvm.R

class GradientTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {


    var startColor: Int = R.color.colorPrimaryDark
    var endColor: Int = R.color.colorMain

    fun setGradientColor(startColor: Int, endColor: Int) {
        this.startColor = startColor
        this.endColor = endColor
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            paint.shader = LinearGradient(
                    0F,
                    0F,
                    width.toFloat() / 2,
                    height.toFloat(),
                    ContextCompat.getColor(context, startColor),
                    ContextCompat.getColor(context, endColor),
                    Shader.TileMode.CLAMP
            )
        }
    }
}