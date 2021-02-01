package com.android.mvvm.core.extension

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

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

fun View.setOnSingleClickListener(listener: View.OnClickListener){
    setOnClickListener(OnSingleClickListener(listener))
}

class OnSingleClickListener : View.OnClickListener{
    private val listener: View.OnClickListener
    private var prevTime = 0L

    constructor(listener: View.OnClickListener){
        this.listener = listener
    }

    constructor(listener: (View) -> Unit){
        this.listener = View.OnClickListener { listener.invoke(it) }
    }

    companion object{
        private const val DELAY = 500L
    }

    override fun onClick(v: View?) {
        val time = System.currentTimeMillis()
        if(time >=  DELAY){
            prevTime = time
            listener.onClick(v)
        }
    }
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}
