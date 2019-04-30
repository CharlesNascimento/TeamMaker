package com.kansus.teammaker.data

import TestData
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.dao.GameDao
import com.kansus.teammaker.android.data.dao.PlayerDao
import com.kansus.teammaker.android.data.entity.GameEntity
import com.kansus.teammaker.android.data.entity.GamePlayerEntity
import TestData.GAME_ENTITY_1
import TestData.GAME_ENTITY_2
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test the implementation of [GameDao]
 */
@RunWith(AndroidJUnit4::class)
class GameDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mDatabase: AppDatabase

    private lateinit var mGameDao: GameDao
    private lateinit var mPlayerDao: PlayerDao

    @Before
    fun initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        mGameDao = mDatabase.gameDao()
        mPlayerDao = mDatabase.playerDao()
    }

    @After
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun getGamesWhenNoGamesInserted() {
        val games = mGameDao.getAll()

        assertThat(games, empty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun getGamesAfterInserted() {
        insertTestGames()

        val games = mGameDao.getAll()

        assertThat(games, hasSize(2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getGameById() {
        insertTestGames()

        val game = mGameDao.get(2)

        assertThat(game, equalTo(GAME_ENTITY_2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertExistingGameUpdatesCurrent() {
        insertTestGames()
        val gameUpdate = GameEntity(1, "Game 3", "Game description 3")

        mGameDao.insert(gameUpdate)

        val game = mGameDao.get(1)
        assertThat(game, equalTo(gameUpdate))
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteGame() {
        insertGamePlayers()

        mGameDao.delete(GAME_ENTITY_1)

        val game = mGameDao.get(1)
        val game1Players = mPlayerDao.getGamePlayers(1)

        assertThat(game, nullValue())
        assertThat(game1Players, empty())
    }

    private fun insertTestGames() {
        mGameDao.insert(GAME_ENTITY_1)
        mGameDao.insert(GAME_ENTITY_2)
    }

    private fun insertTestPlayers() {
        mPlayerDao.insert(TestData.PLAYER_ENTITY_1)
        mPlayerDao.insert(TestData.PLAYER_ENTITY_2)
    }

    private fun insertGamePlayers() {
        insertTestGames()
        insertTestPlayers()

        mGameDao.insert(GamePlayerEntity(1, GAME_ENTITY_1.id, TestData.PLAYER_ENTITY_1.id))
        mGameDao.insert(GamePlayerEntity(2, GAME_ENTITY_1.id, TestData.PLAYER_ENTITY_2.id))
    }
}
