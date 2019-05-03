package com.kansus.teammaker.data.source

import android.database.sqlite.SQLiteException
import com.kansus.teammaker.TestData
import com.kansus.teammaker.UnitTest
import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.dao.GameDao
import com.kansus.teammaker.android.data.dao.PlayerDao
import com.kansus.teammaker.android.data.entity.GamePlayerEntity
import com.kansus.teammaker.android.data.mapper.toGame
import com.kansus.teammaker.android.data.mapper.toGameList
import com.kansus.teammaker.android.data.source.RoomGameDataSource
import com.kansus.teammaker.core.Either.Left
import com.kansus.teammaker.core.Either.Right
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.`should equal`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
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
    fun `get returns right on success`() {
        val expected = TestData.GAME_ENTITY_1
        given { gameDao.get(expected.id) }.willReturn(expected)

        val game = roomGameDataSource.get(expected.id)

        then(gameDao).should().get(expected.id)
        then { game `should equal` Right(expected.toGame()) }
    }

    @Test
    fun `get returns left on failure`() {
        val expected = TestData.GAME_ENTITY_1
        given { gameDao.get(expected.id) }.willThrow(SQLiteException::class.java)

        val game = roomGameDataSource.get(expected.id)

        then(gameDao).should().get(expected.id)
        then { game `should equal` Left(DatabaseError) }
    }

    @Test
    fun `getAll returns right on success`() {
        val expected = TestData.GAME_ENTITIES
        given { gameDao.getAll() }.willReturn(expected)

        val games = roomGameDataSource.getAll()

        then(gameDao).should().getAll()
        then { games `should equal` Right(expected.toGameList()) }
    }

    @Test
    fun `getAll returns left on failure`() {
        given { gameDao.getAll() }.willThrow(SQLiteException::class.java)

        val games = roomGameDataSource.getAll()

        then(gameDao).should().getAll()
        then { games `should equal` Left(DatabaseError) }
    }

    @Test
    fun `insert calls DAO successfully`() {
        val expected = TestData.GAME_ENTITY_1

        roomGameDataSource.insert(expected.toGame())

        then(gameDao).should().insert(expected)
    }

    @Test
    fun `insert returns left on failure`() {
        val expected = TestData.GAME_ENTITY_1
        given { gameDao.insert(expected) }.willThrow(SQLiteException())

        val result = roomGameDataSource.insert(expected.toGame())

        then(gameDao).should().insert(expected)
        then { result `should equal` Left(DatabaseError) }
    }

    @Test
    fun `insertGamePlayer calls DAO successfully`() {
        val expected = GamePlayerEntity(1, 1)

        roomGameDataSource.insertGamePlayer(expected.gameId, expected.playerId)

        then(gameDao).should().insertGamePlayer(expected)
    }

    @Test(expected = SQLiteException::class)
    fun `insertGamePlayer rethrows DAO exception`() {
        val expected = GamePlayerEntity(1, 1)
        given { gameDao.insertGamePlayer(expected) }.willThrow(SQLiteException())

        roomGameDataSource.insertGamePlayer(expected.gameId, expected.playerId)
    }

    @Test
    fun `delete calls DAO successfully`() {
        val expected = TestData.GAME_ENTITY_1

        roomGameDataSource.delete(expected.toGame())

        verify(gameDao).delete(expected)
    }

    @Test
    fun `delete returns left on failure`() {
        val expected = TestData.GAME_ENTITY_1
        given { gameDao.delete(expected) }.willThrow(SQLiteException())

        val result = roomGameDataSource.delete(expected.toGame())

        then(gameDao).should().delete(expected)
        then { result `should equal` Left(DatabaseError) }
    }

    @Test
    fun `deleteGamePlayer calls DAO successfully`() {
        val expected = GamePlayerEntity(1, 1)

        roomGameDataSource.deleteGamePlayer(expected.gameId, expected.playerId)

        verify(gameDao).deleteGamePlayer(expected.gameId, expected.playerId)
    }

    @Test(expected = SQLiteException::class)
    fun `deleteGamePlayer rethrows DAO exception`() {
        val expected = GamePlayerEntity(1, 1)
        given { gameDao.deleteGamePlayer(expected.gameId, expected.playerId) }
            .willThrow(SQLiteException())

        roomGameDataSource.deleteGamePlayer(expected.gameId, expected.playerId)
    }
}