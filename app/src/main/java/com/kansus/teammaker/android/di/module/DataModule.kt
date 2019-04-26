package com.kansus.teammaker.android.di.module

import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.source.RoomFixtureDataSource
import com.kansus.teammaker.android.data.source.RoomGameDataSource
import com.kansus.teammaker.data.repository.DefaultFixtureRepository
import com.kansus.teammaker.data.repository.DefaultGameRepository
import com.kansus.teammaker.data.source.FixtureDataSource
import com.kansus.teammaker.data.source.GameDataSource
import com.kansus.teammaker.domain.repository.FixtureRepository
import com.kansus.teammaker.domain.repository.GameRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideGameRepository(dataSource: GameDataSource): GameRepository =
        DefaultGameRepository(dataSource)

    @Provides
    @Singleton
    fun provideFixtureRepository(dataSource: FixtureDataSource): FixtureRepository =
        DefaultFixtureRepository(dataSource)

    @Provides
    @Singleton
    fun provideGameDataSource(appDatabase: AppDatabase): GameDataSource =
        RoomGameDataSource(appDatabase)


    @Provides
    @Singleton
    fun provideFixtureDataSource(appDatabase: AppDatabase): FixtureDataSource =
        RoomFixtureDataSource(appDatabase)
}