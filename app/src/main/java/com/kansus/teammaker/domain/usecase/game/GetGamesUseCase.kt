package com.kansus.teammaker.domain.usecase.game

import com.kansus.teammaker.core.UseCase
import com.kansus.teammaker.core.UseCase.None
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import javax.inject.Inject

class GetGamesUseCase
@Inject constructor(private val gameRepository: GameRepository) : UseCase<List<Game>, None>() {

    override suspend fun run(params: None) = gameRepository.getAll()
}