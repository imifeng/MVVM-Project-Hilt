package com.android.mvvm.core.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Activity 绑定委托，可以从 onCreate 到 onDestroy（含）使用
 */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline factory: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        factory(layoutInflater)
    }

/**
 * Fragment 绑定委托，可以从 onViewCreated 到 onDestroyView（含）使用
 */
fun <T : ViewBinding> Fragment.viewBinding(factory: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(requireView()).also {
                // 如果在 Lifecycle 被销毁后访问绑定，则创建新实例，但不要缓存它
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                }
            }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)

        }

        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }
    }

/**
 * 实现 onCreateDialog 的 DialogFragment 的绑定委托（像活动一样，它们没有单独的视图生命周期），
 * 可以从 onCreateDialog 到 onDestroy（包括）使用
 */
inline fun <T : ViewBinding> DialogFragment.viewBinding(crossinline factory: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        factory(layoutInflater)
    }

/**
 * 不是真正的委托，只是 RecyclerView.ViewHolders 的一个小帮手
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(factory: (LayoutInflater, ViewGroup, Boolean) -> T) =
    factory(LayoutInflater.from(context), this, false)

/**
 * 不是真正的委托，只是 CustomView 的一个小帮手
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    factory: (LayoutInflater, ViewGroup, Boolean) -> T,
    attachToRoot: Boolean = false
) = factory(LayoutInflater.from(context), this, attachToRoot)