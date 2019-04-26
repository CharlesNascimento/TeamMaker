package com.kansus.teammaker.domain.usecase

import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.UseCase
import com.kansus.teammaker.core.UseCase.None
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import javax.inject.Inject

class DeleteGameUseCase
@Inject constructor(private val gameRepository: GameRepository) : UseCase<None, Game>() {

    override suspend fun run(params: Game): Either<Failure, None> {
        gameRepository.delete(params)
        return Either.Right(None())
    }
}