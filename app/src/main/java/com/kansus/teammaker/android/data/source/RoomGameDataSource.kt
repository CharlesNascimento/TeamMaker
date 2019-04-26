package com.kansus.teammaker.android.data.source

import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.entity.GameEntity
import com.kansus.teammaker.android.data.entity.GamePlayerEntity
import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.data.source.GameDataSource
import com.kansus.teammaker.domain.model.Game

class RoomGameDataSource(
    private val database: AppDatabase
) : GameDataSource {

    override fun get(gameId: Int): Either<Failure, Game> {
        return Either.Right(database.gameDao().get(gameId).toGame())
    }

    override fun getAll(): Either<Failure, List<Game>> {
        return Either.Right(database.gameDao().getAll().toGameList())
    }

    override fun insert(game: Game) {
        database.gameDao().insert(game.toGameEntity())
    }

    override fun insertGamePlayer(gameId: Int, playerId: Int) {
        val gamePlayer = GamePlayerEntity(gameId, playerId)
        database.gameDao().insert(gamePlayer)
    }

    override fun delete(game: Game) {
        database.gameDao().delete(game.toGameEntity())
    }

    override fun deleteGamePlayer(gameId: Int, playerId: Int) {
        database.gameDao().deleteGamePlayer(gameId, playerId)
    }
}

fun Game.toGameEntity() = GameEntity(id, name, description)

fun GameEntity.toGame() = Game(id, name, description)

fun List<GameEntity>.toGameList() = map { it.toGame() }