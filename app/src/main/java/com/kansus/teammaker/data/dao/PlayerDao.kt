package com.kansus.teammaker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kansus.teammaker.data.entity.PlayerEntity

@Dao
interface PlayerDao {

    @Query("SELECT * FROM Player")
    fun getAll(): LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM Player WHERE id = :playerId")
    fun get(playerId: Int): LiveData<PlayerEntity>

    @Query(
        "SELECT p.* FROM GamePlayer tp" +
                " JOIN Player p ON tp.playerId = p.id" +
                " WHERE gameId = :gameId"
    )
    fun getGamePlayers(gameId: Int): LiveData<List<PlayerEntity>>

    @Query(
        "SELECT p.* FROM TeamPlayer tp" +
                " JOIN Player p ON tp.playerId = p.id" +
                " WHERE teamId = :teamId"
    )
    fun getTeamPlayers(teamId: Int): LiveData<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: PlayerEntity)

    @Delete
    fun delete(player: PlayerEntity)
}
