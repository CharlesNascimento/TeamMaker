package com.kansus.teammaker.domain.usecase.game

import com.kansus.teammaker.AndroidTest
import com.kansus.teammaker.TestData.GAME_1
import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.Either.Left
import com.kansus.teammaker.core.Either.Right
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.repository.GameRepository
import com.kansus.teammaker.domain.usecase.game.GetGameByIdUseCase
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock

class GetGameByIdUseCaseTest : AndroidTest() {

    private lateinit var useCase: GetGameByIdUseCase

    @Mock
    private lateinit var gameRepository: GameRepository

    @Before
    fun setUp() {
        useCase = GetGameByIdUseCase(gameRepository)
    }

    @Test
    fun `run returns right on success`() {
        given { gameRepository.get(GAME_1.id) }.willReturn(Right(GAME_1))

        var result: Either<Failure, Game>? = null
        useCase(GAME_1.id) { result = it }

        then(gameRepository).should().get(GAME_1.id)
        then(gameRepository).shouldHaveNoMoreInteractions()
        then { result `should equal` Right(GAME_1) }
    }

    @Test
    fun `run returns left on failure`() {
        given { gameRepository.get(GAME_1.id) }.willReturn(Left(DatabaseError))

        var result: Either<Failure, Game>? = null
        useCase(GAME_1.id) { result = it }

        then(gameRepository).should().get(GAME_1.id)
        then(gameRepository).shouldHaveNoMoreInteractions()
        then { result `should equal` Left(DatabaseError) }
    }
}