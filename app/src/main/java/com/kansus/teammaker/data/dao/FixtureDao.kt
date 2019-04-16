package com.kansus.teammaker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kansus.teammaker.data.entity.FixtureEntity
import com.kansus.teammaker.data.entity.join.FixtureWithTeams

@Dao
interface FixtureDao {

    @Query("SELECT * FROM Fixture where id = :fixtureId")
    fun get(fixtureId: Int): LiveData<FixtureEntity>

    @Transaction
    @Query("SELECT * FROM Fixture where id = :fixtureId")
    fun getWithTeams(fixtureId: Int): LiveData<FixtureWithTeams>

    @Query("SELECT * FROM Fixture")
    fun getAll(): LiveData<List<FixtureEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fixture: FixtureEntity)

    @Delete
    fun delete(fixture: FixtureEntity)
}
