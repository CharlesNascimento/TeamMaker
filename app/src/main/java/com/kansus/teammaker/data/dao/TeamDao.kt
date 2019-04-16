package com.kansus.teammaker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kansus.teammaker.data.entity.TeamEntity
import com.kansus.teammaker.data.entity.TeamPlayerEntity

@Dao
interface TeamDao {

    @Query("SELECT * FROM Team where id = :teamId")
    fun get(teamId: Int): LiveData<TeamEntity>

    @Query("SELECT * FROM Team")
    fun getAll(): LiveData<List<TeamEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: TeamPlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team: TeamEntity)

    @Delete
    fun delete(team: TeamEntity)
}
