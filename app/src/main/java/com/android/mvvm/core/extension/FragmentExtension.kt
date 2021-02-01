package com.android.mvvm.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

/**
 * This extension method will call the [com.vaxcare.core.viewmodel.KodeinViewModelFactory] and
 * inject dependencies into the view model. Requires the view model be registered in the graph
 * before this method can be used. This by default will use the Activity's context for the
 * view model which allows it to be sharable.
 *
 * @param VM The ViewModel type to be used
 * @param T The calling classes Type
 * @return by lazy, the view model that should be inflated
 */
inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
    return lazy { ViewModelProvider(activity!!, direct.instance()).get(VM::class.java) }
}

/**
 * This extension method will call the [com.vaxcare.core.viewmodel.KodeinViewModelFactory] and
 * inject dependencies into the view model. Requires the view model be registered in the graph
 * before this method can be used. This will use the Fragments context so it will be a specific
 * instance for that fragment.
 *
 * @param VM The ViewModel type to be used
 * @param T The calling classes Type
 * @return by lazy, the view model that should be inflated
 */
inline fun <reified VM : ViewModel, T> T.viewModelFragment(): Lazy<VM> where T : KodeinAware, T : Fragment {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}