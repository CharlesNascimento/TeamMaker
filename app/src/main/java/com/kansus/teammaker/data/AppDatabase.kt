package com.kansus.teammaker.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kansus.teammaker.data.converter.DateConverter
import com.kansus.teammaker.data.dao.FixtureDao
import com.kansus.teammaker.data.dao.GameDao
import com.kansus.teammaker.data.dao.PlayerDao
import com.kansus.teammaker.data.dao.TeamDao
import com.kansus.teammaker.data.entity.*

@Database(
    entities = [
        GameEntity::class, GamePlayerEntity::class, PlayerEntity::class, FixtureEntity::class,
        FixtureTeamEntity::class, TeamEntity::class, TeamPlayerEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao
    abstract fun fixtureDao(): FixtureDao

    companion object {

        private var sInstance: AppDatabase? = null

        @VisibleForTesting
        val DATABASE_NAME = "team-maker-db"

        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return sInstance
        }
    }
}
