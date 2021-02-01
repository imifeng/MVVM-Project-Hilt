package com.android.mvvm.di

import com.android.mvvm.data.AppDatabase
import com.android.mvvm.data.dao.RepoBeanDao
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val dataModule = Kodein.Module(name = "dataModule") {
    bind<AppDatabase>() with singleton {
        AppDatabase.initialize(instance(), instance())
    }

    bind<RepoBeanDao>() with singleton {
        instance<AppDatabase>().noteDao()
    }
}
