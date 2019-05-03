package com.kansus.teammaker.data.repository

import android.database.sqlite.SQLiteException
import com.kansus.teammaker.TestData.GAMES
import com.kansus.teammaker.TestData.GAME_1
import com.kansus.teammaker.TestData.GAME_2
import com.kansus.teammaker.TestData.PLAYER_1
import com.kansus.teammaker.UnitTest
import com.kansus.teammaker.core.Either.Left
import com.kansus.teammaker.core.Either.Right
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.data.source.GameDataSource
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock


class DefaultGameRepositoryTest : UnitTest() {

    private lateinit var gameRepository: DefaultGameRepository

    @Mock
    private lateinit var gameDataSource: GameDataSource

    @Before
    fun setUp() {
        gameRepository = DefaultGameRepository(gameDataSource)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `get returns right on success`() {
        given { gameDataSource.get(1) }.willReturn(Right(GAME_1))

        val game = gameRepository.get(1)

        then(gameDataSource).should().get(1)
        game `should equal` (Right(GAME_1))
    }

    @Test
    fun `get returns left on failure`() {
        given { gameDataSource.get(1) }.willReturn(Left(Failure.DatabaseError))

        val game = gameRepository.get(1)

        then(gameDataSource).should().get(1)
        game `should equal` (Left(Failure.DatabaseError))
    }

    @Test
    fun `getAll returns right on success`() {
        given { gameDataSource.getAll() }.willReturn(Right(GAMES))

        val games = gameRepository.getAll()

        then(gameDataSource).should().getAll()
        games `should equal` (Right(GAMES))
        games.either({}) { gameList ->
            gameList `should contain` GAME_1
            gameList `should contain` GAME_2
        }
    }

    @Test
    fun `getAll returns left on failure`() {
        given { gameDataSource.getAll() }.willReturn(Left(Failure.DatabaseError))

        val games = gameRepository.getAll()

        then(gameDataSource).should().getAll()
        games `should equal` (Left(Failure.DatabaseError))
    }

    @Test
    fun `insertGamePlayer calls data source successfully`() {
        val expectedGameId = GAME_1.id
        val expectedPlayerId = PLAYER_1.id

        gameRepository.insertGamePlayer(expectedGameId, expectedPlayerId)

        then(gameDataSource).should().insertGamePlayer(expectedGameId, expectedPlayerId)
    }

    @Test(expected = SQLiteException::class)
    fun `insertGamePlayer rethrows data source exception`() {
        val expectedGameId = GAME_1.id
        val expectedPlayerId = PLAYER_1.id
        given { gameDataSource.insertGamePlayer(expectedGameId, expectedPlayerId) }
            .willThrow(SQLiteException())

        gameRepository.insertGamePlayer(expectedGameId, expectedPlayerId)
    }

    @Test
    fun `insert calls data source successfully`() {
        val expected = GAME_1

        gameRepository.insert(expected)

        then(gameDataSource).should().insert(expected)
    }

    @Test(expected = SQLiteException::class)
    fun `insert rethrows data source exception`() {
        val expected = GAME_1
        given { gameDataSource.insert(expected) }.willThrow(SQLiteException())

        gameRepository.insert(expected)
    }

    @Test
    fun `delete calls data source successfully`() {
        val expected = GAME_1

        gameRepository.delete(expected)

        then(gameDataSource).should().delete(expected)
    }

    @Test(expected = SQLiteException::class)
    fun `delete rethrows data source exception`() {
        val expected = GAME_1
        given { gameDataSource.delete(expected) }.willThrow(SQLiteException())

        gameRepository.delete(expected)
    }

    @Test
    fun `deleteGamePlayer calls data source successfully`() {
        val expectedGameId = GAME_1.id
        val expectedPlayerId = PLAYER_1.id

        gameRepository.deleteGamePlayer(expectedGameId, expectedPlayerId)

        then(gameDataSource).should().deleteGamePlayer(expectedGameId, expectedPlayerId)
    }

    @Test(expected = SQLiteException::class)
    fun `deleteGamePlayer rethrows data source exception`() {
        val expectedGameId = GAME_1.id
        val expectedPlayerId = PLAYER_1.id
        given { gameDataSource.deleteGamePlayer(expectedGameId, expectedPlayerId) }
            .willThrow(SQLiteException())

        gameRepository.deleteGamePlayer(expectedGameId, expectedPlayerId)
    }
}