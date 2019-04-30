package com.kansus.teammaker.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.dao.GameDao
import com.kansus.teammaker.android.data.dao.PlayerDao
import com.kansus.teammaker.android.data.entity.GamePlayerEntity
import com.kansus.teammaker.android.data.entity.PlayerEntity
import TestData.GAME_ENTITY_1
import TestData.GAME_ENTITY_2
import TestData.PLAYER_ENTITY_1
import TestData.PLAYER_ENTITY_2
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.hasSize
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test the implementation of [PlayerDao]
 */
@RunWith(AndroidJUnit4::class)
class PlayerDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mDatabase: AppDatabase

    private lateinit var mGameDao: GameDao
    private lateinit var mPlayerDao: PlayerDao

    @Before
    fun initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
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
    fun getPlayersWhenEmpty() {
        val players = mPlayerDao.getAll()

        assertThat(players, empty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun getPlayersAfterInserted() {
        insertTestPlayers()

        val players = mPlayerDao.getAll()

        assertThat(players, hasSize(2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getPlayerById() {
        insertTestPlayers()

        val player = mPlayerDao.get(2)

        assertThat(player, equalTo(PLAYER_ENTITY_2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getGamePlayers() {
        insertGamePlayers()

        val game1Players = mPlayerDao.getGamePlayers(1)
        val game2Players = mPlayerDao.getGamePlayers(2)

        assertThat(game1Players, hasSize(2))
        assertThat(game2Players, empty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertExistingPlayerUpdatesCurrent() {
        insertTestPlayers()
        val playerUpdate = PlayerEntity(1, "Player 3")

        mPlayerDao.insert(playerUpdate)

        val player = mPlayerDao.get(1)
        assertThat(player, equalTo(playerUpdate))
    }

    @Test
    @Throws(InterruptedException::class)
    fun deletePlayer() {
        insertGamePlayers()

        mPlayerDao.delete(PLAYER_ENTITY_1)

        val player = mPlayerDao.get(1)
        val game1Players = mPlayerDao.getGamePlayers(1)

        assertThat(player, nullValue())
        assertThat(game1Players, hasSize(1))
    }

    private fun insertTestGames() {
        mGameDao.insert(GAME_ENTITY_1)
        mGameDao.insert(GAME_ENTITY_2)
    }

    private fun insertTestPlayers() {
        mPlayerDao.insert(PLAYER_ENTITY_1)
        mPlayerDao.insert(PLAYER_ENTITY_2)
    }

    private fun insertGamePlayers() {
        insertTestGames()
        insertTestPlayers()

        mGameDao.insert(GamePlayerEntity(1, GAME_ENTITY_1.id, PLAYER_ENTITY_1.id))
        mGameDao.insert(GamePlayerEntity(2, GAME_ENTITY_1.id, PLAYER_ENTITY_2.id))
    }
}
