package com.kansus.teammaker.android.di.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kansus.teammaker.android.ui.games.GamesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    abstract fun bindsMoviesViewModel(moviesViewModel: GamesViewModel): ViewModel
}