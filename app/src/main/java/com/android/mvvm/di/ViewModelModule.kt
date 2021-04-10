package com.android.mvvm.di

import com.android.mvvm.viewmodel.KodeinViewModelFactory
import com.android.mvvm.viewmodel.RepoViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bind<KodeinViewModelFactory>() with singleton {
        KodeinViewModelFactory(kodein)
    }

    bind<RepoViewModel>() with singleton {
        RepoViewModel(instance())
    }
}