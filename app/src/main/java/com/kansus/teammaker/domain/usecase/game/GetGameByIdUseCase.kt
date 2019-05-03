package com.kansus.teammaker.domain.usecase.game

import com.kansus.teammaker.core.UseCase
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import javax.inject.Inject

class GetGameByIdUseCase
@Inject constructor(private val gameRepository: GameRepository) : UseCase<Game, Int>() {

    override suspend fun run(params: Int) = gameRepository.get(params)
}