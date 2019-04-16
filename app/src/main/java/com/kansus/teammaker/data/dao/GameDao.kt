package com.kansus.teammaker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kansus.teammaker.data.entity.GameEntity
import com.kansus.teammaker.data.entity.GamePlayerEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM Game where id = :gameId")
    fun get(gameId: Int): LiveData<GameEntity>

    @Query("SELECT * FROM Game")
    fun getAll(): LiveData<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: GamePlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: GameEntity)

    @Delete
    fun delete(game: GameEntity)
}
