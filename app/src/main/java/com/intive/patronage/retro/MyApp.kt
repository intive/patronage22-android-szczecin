package com.intive.patronage.retro

import android.app.Application
import com.intive.patronage.retro.common.dependencyInjection.appModule
import com.intive.patronage.retro.common.dependencyInjection.networkModule
import com.intive.patronage.retro.common.network.CheckNetworkConnect
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties

class MyApp : Application() {

    private val checkNet: CheckNetworkConnect by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            fileProperties()
            androidContext(this@MyApp)
            modules(appModule, networkModule)
        }
        checkNet.start()
    }

    override fun onTerminate() {
        super.onTerminate()
        checkNet.stop()
    }
}
