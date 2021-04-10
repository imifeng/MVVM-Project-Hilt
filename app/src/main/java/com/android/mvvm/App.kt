package com.android.mvvm

import android.app.Application
import com.android.mvvm.di.dataModule
import com.android.mvvm.di.viewModelModule
import com.android.mvvm.di.webModule
import com.android.mvvm.service.SharedPrefService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App: Application(), KodeinAware {

    companion object {
        private const val TAG = "App"
    }

    override val kodein by Kodein.lazy {
        bind<App>() with singleton { this@App }
        bind<SharedPrefService>() with singleton {
            SharedPrefService(this@App)
        }
        import(dataModule)
        import(webModule)
        import(viewModelModule)
//        import(serviceModule)
    }

    override fun onCreate() {
        super.onCreate()

    }

}