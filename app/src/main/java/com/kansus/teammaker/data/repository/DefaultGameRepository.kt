package com.kansus.teammaker.data.repository

import com.kansus.teammaker.data.source.GameDataSource
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import javax.inject.Inject

class DefaultGameRepository @Inject constructor(
    private val gameDataSource: GameDataSource
) : GameRepository {

    override fun get(gameId: Int) = gameDataSource.get(gameId)

    override fun getAll() = gameDataSource.getAll()

    override fun insert(game: Game) = gameDataSource.insert(game)

    override fun delete(game: Game) = gameDataSource.delete(game)

    override fun insertGamePlayer(gameId: Int, playerId: Int) {
        gameDataSource.insertGamePlayer(gameId, playerId)
    }

    override fun deleteGamePlayer(gameId: Int, playerId: Int) {
        gameDataSource.deleteGamePlayer(gameId, playerId)
    }
}