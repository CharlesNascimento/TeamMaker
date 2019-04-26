package com.kansus.teammaker.android.di.component

import com.kansus.teammaker.android.AndroidApplication
import com.kansus.teammaker.android.di.builder.ActivityBuilder
import com.kansus.teammaker.android.di.builder.FragmentBuilder
import com.kansus.teammaker.android.di.module.ApplicationModule
import com.kansus.teammaker.android.di.module.DataModule
import com.kansus.teammaker.android.di.module.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ViewModelModule::class,
        DataModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class
    ]
)
interface AppComponent : AndroidInjector<AndroidApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AndroidApplication>()
}