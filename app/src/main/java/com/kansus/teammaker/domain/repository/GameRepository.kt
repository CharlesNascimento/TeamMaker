package com.kansus.teammaker.domain.repository

import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.domain.model.Game

interface GameRepository {

    fun get(gameId: Int): Either<Failure, Game>

    fun getAll(): Either<Failure, List<Game>>

    fun insertGamePlayer(gameId: Int, playerId: Int)

    fun insert(game: Game)

    fun delete(game: Game)

    fun deleteGamePlayer(gameId: Int, playerId: Int)
}