package com.kansus.teammaker.android.di.builder

import com.kansus.teammaker.android.ui.fixtures.FixturesFragment
import com.kansus.teammaker.android.ui.games.GamesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideGamesFragmentFactory(): GamesFragment

    @ContributesAndroidInjector
    abstract fun provideFixturesFragmentFactory(): FixturesFragment
}