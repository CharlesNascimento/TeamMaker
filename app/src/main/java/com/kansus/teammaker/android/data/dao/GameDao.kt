package com.kansus.teammaker.android.data.dao

import androidx.room.*
import com.kansus.teammaker.android.data.entity.GameEntity
import com.kansus.teammaker.android.data.entity.GamePlayerEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM Game where id = :gameId")
    fun get(gameId: Int): GameEntity

    @Query("SELECT * FROM Game")
    fun getAll(): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: GamePlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: GameEntity)

    @Delete
    fun delete(game: GameEntity)

    @Query("DELETE FROM GamePlayer WHERE gameId = :gameId AND playerId = :playerId")
    fun deleteGamePlayer(gameId: Int, playerId: Int)
}
