package com.android.mvvm.core.extension

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * A simple extension method to return the layout inflater from the context object.
 *
 * @return the system Layout Inflater
 */
fun Context.getLayoutInflater(): LayoutInflater {
    return this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}

/**
 * TODO
 *
 * @return
 */
fun Context.getWifiManager(): WifiManager {
    return this.getSystemService(Context.WIFI_SERVICE) as WifiManager
}

/**
 * Given a context, fetch the main looper so that we can post messages on the UI thread
 *
 * @param function anonymous function to run in the Handler
 */
fun Context.getMainThread(function: () -> Unit) {
    Handler(this.mainLooper).post(function)
}

/**
 * A method to hide the soft keyboard when on the screen
 *
 * @param view - this can literally be any view on screen
 */
fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Show a long toast message on the UI with a given string placeholder
 *
 * This method is deprecated and should not be used except for in testing. Please use the
 * [makeLongToast] with a resource id instead.
 *
 * @param string the message to be displayed
 */
fun Context.makeLongToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_LONG).show()
}

/**
 * Show a long toast message on the UI with a given string resource id
 *
 * @param string the resource id to be displayed
 */
fun Context.makeLongToast(@StringRes string: Int) {
    Toast.makeText(this, string, Toast.LENGTH_LONG).show()
}

/**
 * Show a short toast message on the UI with a given string placeholder
 *
 * This method is deprecated and should not be used except for in testing. Please use the
 * [makeShortToast] with a resource id instead.
 *
 * @param string the message to be displayed
 */
fun Context.makeShortToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

/**
 * Show a short toast message on the UI with a given string resource id
 *
 * @param string the resource id to be displayed
 */
fun Context.makeShortToast(@StringRes string: Int) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}