package com.kansus.teammaker.domain.usecase.game

import com.kansus.teammaker.AndroidTest
import com.kansus.teammaker.TestData.GAMES
import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.Either.Left
import com.kansus.teammaker.core.Either.Right
import com.kansus.teammaker.core.UseCase
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import com.kansus.teammaker.domain.usecase.game.GetGamesUseCase
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock

class GetGamesUseCaseTest : AndroidTest() {

    private lateinit var useCase: GetGamesUseCase

    @Mock
    private lateinit var gameRepository: GameRepository

    @Before
    fun setUp() {
        useCase = GetGamesUseCase(gameRepository)
    }

    @Test
    fun `run returns right on success`() {
        given { gameRepository.getAll() }.willReturn(Right(GAMES))

        var result: Either<Failure, List<Game>>? = null
        useCase(UseCase.None()) { result = it }

        then(gameRepository).should().getAll()
        then(gameRepository).shouldHaveNoMoreInteractions()
        then { result `should equal` Right(GAMES) }
    }

    @Test
    fun `run returns left on failure`() {
        given { gameRepository.getAll() }.willReturn(Left(DatabaseError))

        var result: Either<Failure, List<Game>>? = null
        useCase(UseCase.None()) { result = it }

        then(gameRepository).should().getAll()
        then(gameRepository).shouldHaveNoMoreInteractions()
        then { result `should equal` Left(DatabaseError) }
    }
}