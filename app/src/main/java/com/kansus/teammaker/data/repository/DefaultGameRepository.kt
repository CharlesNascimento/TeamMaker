package com.kansus.teammaker.data.repository

import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.data.source.GameDataSource
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import javax.inject.Inject

class DefaultGameRepository @Inject constructor(
    private val gameDataSource: GameDataSource
) : GameRepository {

    override fun get(gameId: Int): Either<Failure, Game> {
        return gameDataSource.get(gameId)
    }

    override fun getAll(): Either<Failure, List<Game>> {
        return gameDataSource.getAll()
    }

    override fun insertGamePlayer(gameId: Int, playerId: Int) {
        gameDataSource.insertGamePlayer(gameId, playerId)
    }

    override fun insert(game: Game) {
        gameDataSource.insert(game)
    }

    override fun delete(game: Game) {
        gameDataSource.delete(game)
    }

    override fun deleteGamePlayer(gameId: Int, playerId: Int) {
        gameDataSource.deleteGamePlayer(gameId, playerId)
    }
}