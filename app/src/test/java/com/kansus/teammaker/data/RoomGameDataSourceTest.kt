package com.kansus.teammaker.data

import TestData
import com.kansus.teammaker.UnitTest
import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.dao.GameDao
import com.kansus.teammaker.android.data.dao.PlayerDao
import com.kansus.teammaker.android.data.entity.GamePlayerEntity
import com.kansus.teammaker.android.data.source.RoomGameDataSource
import com.kansus.teammaker.android.data.source.toGame
import com.kansus.teammaker.android.data.source.toGameList
import com.kansus.teammaker.core.Either.Right
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.`should equal`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class RoomGameDataSourceTest : UnitTest() {

    private lateinit var roomGameDataSource: RoomGameDataSource

    @Mock
    private lateinit var appDatabase: AppDatabase

    @Mock
    private lateinit var gameDao: GameDao

    @Mock
    private lateinit var playerDao: PlayerDao

    @Before
    fun setUp() {
        roomGameDataSource = RoomGameDataSource(appDatabase)

        given { appDatabase.gameDao() }.willReturn(gameDao)
        //given { appDatabase.playerDao() }.willReturn(playerDao)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun get() {
        val expected = TestData.GAME_ENTITY_1
        given { gameDao.get(expected.id) }.willReturn(expected)

        val game = roomGameDataSource.get(expected.id)

        verify(gameDao).get(expected.id)
        assert(game.isRight)
        game `should equal` Right(expected.toGame())
    }

    @Test
    fun getAll() {
        val expected = TestData.GAMES
        given { gameDao.getAll() }.willReturn(expected)

        val game = roomGameDataSource.getAll()

        verify(gameDao).getAll()
        assert(game.isRight)
        game `should equal` Right(expected.toGameList())
    }

    @Test
    fun insert() {
        val expected = TestData.GAME_ENTITY_1

        roomGameDataSource.insert(expected.toGame())

        verify(gameDao).insert(expected)
    }

    @Test
    fun insertGamePlayer() {
        val expected = GamePlayerEntity(1, 1)

        roomGameDataSource.insertGamePlayer(expected.gameId, expected.playerId)

        verify(gameDao).insert(expected)
    }

    @Test
    fun delete() {
        val expected = TestData.GAME_ENTITY_1

        roomGameDataSource.delete(expected.toGame())

        verify(gameDao).delete(expected)
    }

    @Test
    fun deleteGamePlayer() {
        val expected = GamePlayerEntity(1, 1)

        roomGameDataSource.deleteGamePlayer(expected.gameId, expected.playerId)

        verify(gameDao).deleteGamePlayer(expected.gameId, expected.playerId)
    }
}