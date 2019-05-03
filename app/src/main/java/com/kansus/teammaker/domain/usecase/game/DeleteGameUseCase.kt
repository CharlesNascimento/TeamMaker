package com.kansus.teammaker.domain.usecase.game

import com.kansus.teammaker.core.UseCase
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import javax.inject.Inject

class DeleteGameUseCase
@Inject constructor(private val gameRepository: GameRepository) : UseCase<Unit, Game>() {

    override suspend fun run(params: Game) = gameRepository.delete(params)
}