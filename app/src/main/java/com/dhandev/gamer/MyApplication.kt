package com.dhandev.gamer

import android.app.Application
import com.dhandev.gamer.core.di.databaseModule
import com.dhandev.gamer.core.di.networkModule
import com.dhandev.gamer.core.di.repositoryModule
import com.dhandev.gamer.di.useCaseModule
import com.dhandev.gamer.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(databaseModule, networkModule, repositoryModule, useCaseModule, viewModelModule, )
        }
    }
}