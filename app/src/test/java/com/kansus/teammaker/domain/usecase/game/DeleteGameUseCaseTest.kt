package com.kansus.teammaker.domain.usecase.game

import com.kansus.teammaker.AndroidTest
import com.kansus.teammaker.TestData.GAME_1
import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.Either.Left
import com.kansus.teammaker.core.Either.Right
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.kansus.teammaker.domain.repository.GameRepository
import com.kansus.teammaker.domain.usecase.game.DeleteGameUseCase
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock

class DeleteGameUseCaseTest : AndroidTest() {

    private lateinit var useCase: DeleteGameUseCase

    @Mock
    private lateinit var gameRepository: GameRepository

    @Before
    fun setUp() {
        useCase = DeleteGameUseCase(gameRepository)
    }

    @Test
    fun `run returns right on success`() {
        given { gameRepository.delete(GAME_1) }.willReturn(Right(Unit))

        var result: Either<Failure, Unit>? = null
        useCase(GAME_1) { result = it }

        then(gameRepository).should().delete(GAME_1)
        then(gameRepository).shouldHaveNoMoreInteractions()
        then { result `should equal` Right(Unit) }
    }

    @Test
    fun `run returns left on failure`() {
        given { gameRepository.delete(GAME_1) }.willReturn(Left(DatabaseError))

        var result: Either<Failure, Unit>? = null
        useCase(GAME_1) { result = it }

        then(gameRepository).should().delete(GAME_1)
        then(gameRepository).shouldHaveNoMoreInteractions()
        then { result `should equal` Left(DatabaseError) }
    }
}