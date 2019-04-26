package com.kansus.teammaker.android

import com.kansus.teammaker.BuildConfig
import com.kansus.teammaker.android.di.component.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AndroidApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        this.initializeLeakDetection()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}