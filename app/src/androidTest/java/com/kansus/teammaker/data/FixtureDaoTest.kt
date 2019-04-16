package com.kansus.teammaker.data

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.kansus.teammaker.LiveDataTestUtil
import com.kansus.teammaker.data.TestData.FIXTURE_ENTITY_1
import com.kansus.teammaker.data.TestData.FIXTURE_ENTITY_2
import com.kansus.teammaker.data.TestData.GAME_ENTITY_1
import com.kansus.teammaker.data.TestData.GAME_ENTITY_2
import com.kansus.teammaker.data.TestData.PLAYER_ENTITY_1
import com.kansus.teammaker.data.TestData.PLAYER_ENTITY_2
import com.kansus.teammaker.data.dao.FixtureDao
import com.kansus.teammaker.data.dao.GameDao
import com.kansus.teammaker.data.dao.PlayerDao
import com.kansus.teammaker.data.dao.TeamDao
import com.kansus.teammaker.data.entity.FixtureEntity
import com.kansus.teammaker.data.entity.GamePlayerEntity
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
import java.util.*

/**
 * Test the implementation of [FixtureDao]
 */
@RunWith(AndroidJUnit4::class)
class FixtureDaoTest {

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
            InstrumentationRegistry.getContext(),
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
    fun getFixturesWhenEmpty() {
        val fixtures = LiveDataTestUtil.getValue(mFixtureDao.getAll())

        assertThat(fixtures, empty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun getFixturesAfterInserted() {
        insertTestData()

        val fixtures = LiveDataTestUtil.getValue(mFixtureDao.getAll())

        assertThat(fixtures, hasSize(2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getFixtureById() {
        insertTestData()

        val fixture = LiveDataTestUtil.getValue(mFixtureDao.get(2))

        assertThat(fixture, equalTo(FIXTURE_ENTITY_2))
    }

    @Test
    @Throws(InterruptedException::class)
    fun getFixtureTeams() {
        insertTestData()

        val fixture1 = LiveDataTestUtil.getValue(mFixtureDao.getWithTeams(1))
        val fixture2 = LiveDataTestUtil.getValue(mFixtureDao.getWithTeams(2))

        assertThat(fixture1.teams, hasSize(2))
        assertThat(fixture2.teams, empty())
    }

    @Test(expected = SQLiteConstraintException::class)
    @Throws(InterruptedException::class)
    fun insertWithoutGameThrows() {
        val fixtureToInsert = FixtureEntity(3, Date(), 0)

        mFixtureDao.insert(fixtureToInsert)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertExistingFixtureUpdatesCurrent() {
        insertTestData()
        val fixtureUpdate = FixtureEntity(1, Date(), GAME_ENTITY_1.id)

        mFixtureDao.insert(fixtureUpdate)

        val fixture = LiveDataTestUtil.getValue(mFixtureDao.get(1))
        assertThat(fixture, equalTo(fixtureUpdate))
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteFixture() {
        insertTestData()

        mFixtureDao.delete(FIXTURE_ENTITY_1)

        val fixture = LiveDataTestUtil.getValue(mFixtureDao.getWithTeams(1))
        val team1 = LiveDataTestUtil.getValue(mTeamDao.get(1))
        val team2 = LiveDataTestUtil.getValue(mTeamDao.get(1))

        assertThat(fixture, nullValue())
        assertThat(team1, nullValue())
        assertThat(team2, nullValue())
    }

    private fun insertTestData() {
        mGameDao.insert(GAME_ENTITY_1)
        mGameDao.insert(GAME_ENTITY_2)

        mPlayerDao.insert(PLAYER_ENTITY_1)
        mPlayerDao.insert(PLAYER_ENTITY_2)

        mGameDao.insert(GamePlayerEntity(1, GAME_ENTITY_1.id, TestData.PLAYER_ENTITY_1.id))
        mGameDao.insert(GamePlayerEntity(2, GAME_ENTITY_1.id, TestData.PLAYER_ENTITY_2.id))

        mFixtureDao.insert(FIXTURE_ENTITY_1)
        mFixtureDao.insert(FIXTURE_ENTITY_2)

        mTeamDao.insert(TestData.TEAM_ENTITY_1)
        mTeamDao.insert(TestData.TEAM_ENTITY_2)
    }
}
