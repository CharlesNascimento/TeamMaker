package com.kansus.teammaker.android.data.dao

import androidx.room.*
import com.kansus.teammaker.android.data.entity.PlayerEntity

@Dao
interface PlayerDao {

    @Query("SELECT * FROM Player")
    fun getAll(): List<PlayerEntity>

    @Query("SELECT * FROM Player WHERE id = :playerId")
    fun get(playerId: Int): PlayerEntity

    @Query(
        "SELECT p.* FROM GamePlayer tp" +
                " JOIN Player p ON tp.playerId = p.id" +
                " WHERE gameId = :gameId"
    )
    fun getGamePlayers(gameId: Int): List<PlayerEntity>

    @Query(
        "SELECT p.* FROM TeamPlayer tp" +
                " JOIN Player p ON tp.playerId = p.id" +
                " WHERE teamId = :teamId"
    )
    fun getTeamPlayers(teamId: Int): List<PlayerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: PlayerEntity)

    @Delete
    fun delete(player: PlayerEntity)
}
