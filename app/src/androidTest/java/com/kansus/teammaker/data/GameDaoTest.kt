package com.kansus.teammaker.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.kansus.teammaker.LiveDataTestUtil
import com.kansus.teammaker.data.TestData.GAME_ENTITY_1
import com.kansus.teammaker.data.TestData.GAME_ENTITY_2
import com.kansus.teammaker.data.dao.GameDao
import com.kansus.teammaker.data.dao.PlayerDao
import com.kansus.teammaker.data.entity.GameEntity
import com.kansus.teammaker.data.entity.GamePlayerEntity
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
            InstrumentationRegistry.getContext(),
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
        val games = LiveDataTestUtil.getValue(mGameDao.getAll())

        assertThat(games, empty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun getGamesAfterInserted() {
        insertTestGames()

        val games = LiveDataTestUtil.getValue(mGameDao.getAll())

        assertThat(games, hasSize(2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getGameById() {
        insertTestGames()

        val game = LiveDataTestUtil.getValue(mGameDao.get(2))

        assertThat(game, equalTo(GAME_ENTITY_2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertExistingGameUpdatesCurrent() {
        insertTestGames()
        val gameUpdate = GameEntity(1, "Game 3", "Game description 3")

        mGameDao.insert(gameUpdate)

        val game = LiveDataTestUtil.getValue(mGameDao.get(1))
        assertThat(game, equalTo(gameUpdate))
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteGame() {
        insertGamePlayers()

        mGameDao.delete(GAME_ENTITY_1)

        val game = LiveDataTestUtil.getValue(mGameDao.get(1))
        val game1Players = LiveDataTestUtil.getValue(mPlayerDao.getGamePlayers(1))

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
