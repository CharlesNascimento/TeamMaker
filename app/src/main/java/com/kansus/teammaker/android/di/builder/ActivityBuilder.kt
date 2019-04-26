package com.kansus.teammaker.android.di.builder

import com.kansus.teammaker.android.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Pravin Divraniya on 10/6/2017.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}