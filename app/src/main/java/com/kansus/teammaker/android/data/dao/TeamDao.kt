package com.kansus.teammaker.android.data.dao

import androidx.room.*
import com.kansus.teammaker.android.data.entity.TeamEntity
import com.kansus.teammaker.android.data.entity.TeamPlayerEntity

@Dao
interface TeamDao {

    @Query("SELECT * FROM Team where id = :teamId")
    fun get(teamId: Int): TeamEntity

    @Query("SELECT * FROM Team")
    fun getAll(): List<TeamEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: TeamPlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team: TeamEntity)

    @Delete
    fun delete(team: TeamEntity)
}
