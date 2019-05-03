package com.kansus.teammaker.android.data.source

import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.entity.GamePlayerEntity
import com.kansus.teammaker.android.data.mapper.toGame
import com.kansus.teammaker.android.data.mapper.toGameEntity
import com.kansus.teammaker.android.data.mapper.toGameList
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.kansus.teammaker.core.runEither
import com.kansus.teammaker.data.source.GameDataSource
import com.kansus.teammaker.domain.model.Game

class RoomGameDataSource(
    private val database: AppDatabase
) : GameDataSource {

    override fun get(gameId: Int) = runEither(DatabaseError) {
        database.gameDao().get(gameId).toGame()
    }

    override fun getAll() = runEither(DatabaseError) {
        database.gameDao().getAll().toGameList()
    }

    override fun insert(game: Game) = runEither(DatabaseError) {
        database.gameDao().insert(game.toGameEntity())
    }

    override fun insertGamePlayer(gameId: Int, playerId: Int) {
        val gamePlayer = GamePlayerEntity(gameId, playerId)
        database.gameDao().insertGamePlayer(gamePlayer)
    }

    override fun delete(game: Game) = runEither(DatabaseError) {
        database.gameDao().delete(game.toGameEntity())
    }

    override fun deleteGamePlayer(gameId: Int, playerId: Int) {
        database.gameDao().deleteGamePlayer(gameId, playerId)
    }
}
