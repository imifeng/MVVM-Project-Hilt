package com.android.mvvm.core.extension

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.android.mvvm.R

/**
 * Sets the background color for an element given a primary color res
 *
 * @param primaryColorRes
 */
fun View.setBackgroundColorRes(@ColorRes primaryColorRes: Int) {
    val primaryColor =
        ContextCompat.getColor(context, primaryColorRes)
    this.setBackgroundColor(primaryColor)
}

fun TextView.setTextColorRes(@ColorRes primaryColorRes: Int) {
    val primaryColor =
        ContextCompat.getColor(context, primaryColorRes)
    this.setTextColor(primaryColor)
}

fun View.setOnSingleClickListener(listener: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(listener))
}

class OnSingleClickListener : View.OnClickListener {
    private val listener: View.OnClickListener
    private var prevTime = 0L

    constructor(listener: (View) -> Unit) {
        this.listener = View.OnClickListener { listener.invoke(it) }
    }

    companion object {
        private const val DELAY = 500L
    }

    override fun onClick(v: View?) {
        val time = System.currentTimeMillis()
        if (time - prevTime >= DELAY) {
            prevTime = time
            listener.onClick(v)
        }
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.clipOutlineCornerRadius(radius: Float = 10F) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            if (view != null && outline != null) {
                outline.setRoundRect(0, 0, view.width, view.height, radius)
            }
        }
    }
    clipToOutline = true
}

fun View.adaptStatusBarHeight() {
    val pH = getStatusBarHeight(context)
    if (pH > 0) {
        val params = this.layoutParams
        params.height = pH
        layoutParams = params
    }
}

/**
 * get statusBar height
 *
 * @param context
 * @return statusBar height
 */
private fun getStatusBarHeight(context: Context): Int {
    try {
        var result = 0
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return 0
}

fun View.toReviewByAd(isReview: Boolean) {
    this.visibility = if (isReview) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun View.toAnimForShow() {
    this.animate().alpha(1F).setDuration(300).start()
}

fun View.toAnimForHide() {
    this.animate().alpha(0F).setDuration(300).start()
}


fun View.toAnimForBtn() {
    val animationSet = AnimationSet(true).apply {
        addAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_btn_view))
    }
    this.startAnimation(animationSet)
}

fun View.toAnimForHeartbeatBtn() {
    val animationSet = AnimationSet(true).apply {
        addAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_heartbeat_btn_view))
    }
    this.startAnimation(animationSet)
}

fun View.toAnimForHeartbeatRoundBg() {
    val animationSet = AnimationSet(true).apply {
        addAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_heartbeat_bg_view))
    }
    this.startAnimation(animationSet)
}

fun View.stopAnim(isHide: Boolean = false) {
    this.clearAnimation()
    if (isHide) {
        this.hide()
    }
}

