package com.intive.patronage.retro

import android.app.Application
import com.intive.patronage.retro.common.dependencyInjection.appModule
import com.intive.patronage.retro.common.dependencyInjection.networkModule
import com.intive.patronage.retro.common.network.NetworkConnectionObserver
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties

class MyApp : Application() {

    private val net: NetworkConnectionObserver by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            fileProperties()
            androidContext(this@MyApp)
            modules(appModule, networkModule)
        }
        net.start()
    }

    override fun onTerminate() {
        super.onTerminate()
        net.stop()
    }
}
