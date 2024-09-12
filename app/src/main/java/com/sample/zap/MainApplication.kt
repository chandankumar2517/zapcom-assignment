package com.sample.zap

import android.app.Application
import com.sample.zap.core.di.networkModule
import com.sample.zap.core.di.repoModule
import com.sample.zap.core.di.sharedPrefModule
import com.sample.zap.core.di.useCaseModule
import com.sample.zap.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@MainApplication)

            modules(listOf(viewModelModule, repoModule, networkModule, useCaseModule, sharedPrefModule))

        }
    }

}




