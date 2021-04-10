package com.android.mvvm.core.extension

import android.app.Activity
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.mvvm.R
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

/**
 * This extension method will call the [com.sxiaozhi.netspeed.viewmodel.KodeinViewModelFactory] and
 * inject dependencies into the view model. Requires the view model be registered in the graph
 * before this method can be used.
 *
 * @param VM The ViewModel type to be used
 * @param T The calling classes Type
 * @return by lazy, the view model that should be inflated
 */
inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : AppCompatActivity {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}

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