package com.kansus.teammaker.android.data.dao

import androidx.room.*
import com.kansus.teammaker.android.data.entity.FixtureEntity
import com.kansus.teammaker.android.data.entity.join.FixtureWithTeams

@Dao
interface FixtureDao {

    @Query("SELECT * FROM Fixture WHERE id = :fixtureId")
    fun get(fixtureId: Int): FixtureEntity

    @Transaction
    @Query("SELECT * FROM Fixture WHERE id = :fixtureId")
    fun getWithTeams(fixtureId: Int): FixtureWithTeams

    @Query("SELECT * FROM Fixture WHERE gameId = :gameId")
    fun getAll(gameId: Int): List<FixtureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fixture: FixtureEntity)

    @Delete
    fun delete(fixture: FixtureEntity)
}
