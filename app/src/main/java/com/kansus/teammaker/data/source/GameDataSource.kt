package com.kansus.teammaker.data.source

import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.domain.model.Game

interface GameDataSource {

    fun get(gameId: Int): Either<Failure, Game>

    fun getAll(): Either<Failure, List<Game>>

    fun insert(game: Game): Either<Failure, Unit>

    fun insertGamePlayer(gameId: Int, playerId: Int)

    fun delete(game: Game): Either<Failure, Unit>

    fun deleteGamePlayer(gameId: Int, playerId: Int)
}