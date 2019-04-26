package com.kansus.teammaker.android.di.module

import android.content.Context
import com.kansus.teammaker.android.AndroidApplication
import com.kansus.teammaker.android.Navigator
import com.kansus.teammaker.android.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: AndroidApplication): Context = application

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context) = AppDatabase.getInstance(context)!!

    @Singleton
    @Provides
    fun provideNavigator() = Navigator()
}