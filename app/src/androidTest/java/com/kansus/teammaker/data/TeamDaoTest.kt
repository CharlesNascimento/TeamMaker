package com.kansus.teammaker.data

import com.kansus.teammaker.TestData
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.dao.FixtureDao
import com.kansus.teammaker.android.data.dao.GameDao
import com.kansus.teammaker.android.data.dao.PlayerDao
import com.kansus.teammaker.android.data.dao.TeamDao
import com.kansus.teammaker.android.data.entity.GamePlayerEntity
import com.kansus.teammaker.android.data.entity.TeamEntity
import com.kansus.teammaker.android.data.entity.TeamPlayerEntity
import com.kansus.teammaker.TestData.FIXTURE_ENTITY_1
import com.kansus.teammaker.TestData.FIXTURE_ENTITY_2
import com.kansus.teammaker.TestData.GAME_ENTITY_1
import com.kansus.teammaker.TestData.GAME_ENTITY_2
import com.kansus.teammaker.TestData.PLAYER_ENTITY_1
import com.kansus.teammaker.TestData.PLAYER_ENTITY_2
import com.kansus.teammaker.TestData.TEAM_ENTITY_1
import com.kansus.teammaker.TestData.TEAM_ENTITY_2
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Test the implementation of [TeamDao]
 */
@RunWith(AndroidJUnit4::class)
class TeamDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mDatabase: AppDatabase

    private lateinit var mGameDao: GameDao
    private lateinit var mPlayerDao: PlayerDao
    private lateinit var mTeamDao: TeamDao
    private lateinit var mFixtureDao: FixtureDao

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
        mTeamDao = mDatabase.teamDao()
        mFixtureDao = mDatabase.fixtureDao()
    }

    @After
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun getTeamsWhenEmpty() {
        val teams = mTeamDao.getAll()

        assertThat(teams, empty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun getTeamsAfterInserted() {
        insertTestData()

        val teams = mTeamDao.getAll()

        assertThat(teams, hasSize(2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getTeamById() {
        insertTestData()

        val team = mTeamDao.get(2)

        assertThat(team, equalTo(TEAM_ENTITY_2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getTeamPlayers() {
        insertTestData()

        val team1Players = mPlayerDao.getTeamPlayers(1)
        val team2Players = mPlayerDao.getTeamPlayers(2)

        assertThat(team1Players, hasSize(2))
        assertThat(team2Players, empty())
    }

    @Test(expected = SQLiteConstraintException::class)
    @Throws(InterruptedException::class)
    fun insertWithoutFixtureThrows() {
        val teamToInsert = TeamEntity(3, Date(), 0)

        mTeamDao.insert(teamToInsert)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertExistingTeamUpdatesCurrent() {
        insertTestData()
        val teamUpdate = TeamEntity(1, Date(), FIXTURE_ENTITY_1.id)

        mTeamDao.insert(teamUpdate)

        val team = mTeamDao.get(1)
        assertThat(team, equalTo(teamUpdate))
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteTeam() {
        insertTestData()

        mTeamDao.delete(TEAM_ENTITY_1)

        val team = mTeamDao.get(1)

        assertThat(team, nullValue())
    }

    private fun insertTestData() {
        mGameDao.insert(GAME_ENTITY_1)
        mGameDao.insert(GAME_ENTITY_2)

        mPlayerDao.insert(PLAYER_ENTITY_1)
        mPlayerDao.insert(PLAYER_ENTITY_2)

        mGameDao.insertGamePlayer(GamePlayerEntity(1, GAME_ENTITY_1.id, TestData.PLAYER_ENTITY_1.id))
        mGameDao.insertGamePlayer(GamePlayerEntity(2, GAME_ENTITY_1.id, TestData.PLAYER_ENTITY_2.id))

        mFixtureDao.insert(FIXTURE_ENTITY_1)
        mFixtureDao.insert(FIXTURE_ENTITY_2)

        mTeamDao.insert(TEAM_ENTITY_1)
        mTeamDao.insert(TEAM_ENTITY_2)

        mTeamDao.insert(TeamPlayerEntity(1, TEAM_ENTITY_1.id, PLAYER_ENTITY_1.id))
        mTeamDao.insert(TeamPlayerEntity(2, TEAM_ENTITY_1.id, PLAYER_ENTITY_2.id))
    }
}
