package com.kansus.teammaker.data.source

import android.database.sqlite.SQLiteException
import com.kansus.teammaker.TestData
import com.kansus.teammaker.TestData.GAME_1
import com.kansus.teammaker.UnitTest
import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.dao.FixtureDao
import com.kansus.teammaker.android.data.entity.join.FixtureWithTeams
import com.kansus.teammaker.android.data.mapper.toFixture
import com.kansus.teammaker.android.data.mapper.toFixtureList
import com.kansus.teammaker.android.data.source.RoomFixtureDataSource
import com.kansus.teammaker.core.Either.Left
import com.kansus.teammaker.core.Either.Right
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.`should equal`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class RoomFixtureDataSourceTest : UnitTest() {

    private lateinit var roomFixtureDataSource: RoomFixtureDataSource

    @Mock
    private lateinit var appDatabase: AppDatabase

    @Mock
    private lateinit var fixtureDao: FixtureDao

    @Before
    fun setUp() {
        roomFixtureDataSource = RoomFixtureDataSource(appDatabase)

        given { appDatabase.fixtureDao() }.willReturn(fixtureDao)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get returns right on success`() {
        val expected = FixtureWithTeams(TestData.FIXTURE_ENTITY_1, listOf())
        given { fixtureDao.getWithTeams(any()) }.willReturn(expected)

        val fixture = roomFixtureDataSource.get(1)

        verify(fixtureDao).getWithTeams(any())
        fixture `should equal` Right(expected.toFixture())
    }

    @Test
    fun `get returns left on failure`() {
        given { fixtureDao.getWithTeams(any()) }.willThrow(SQLiteException())

        val fixture = roomFixtureDataSource.get(1)

        verify(fixtureDao).getWithTeams(1)
        fixture `should equal` Left(DatabaseError)
    }

    @Test
    fun `getAll returns right on success`() {
        val expected = TestData.FIXTURE_ENTITIES
        given { fixtureDao.getAll(any()) }.willReturn(expected)

        val fixture = roomFixtureDataSource.getAll(GAME_1.id)

        verify(fixtureDao).getAll(GAME_1.id)
        fixture `should equal` Right(expected.toFixtureList())
    }

    @Test
    fun `getAll returns left on failure`() {
        given { fixtureDao.getAll(any()) }.willThrow(SQLiteException())

        val fixture = roomFixtureDataSource.getAll(GAME_1.id)

        verify(fixtureDao).getAll(GAME_1.id)
        fixture `should equal` Left(DatabaseError)
    }

    @Test
    fun `insert calls DAO successfully`() {
        val expected = TestData.FIXTURE_ENTITY_1

        roomFixtureDataSource.insert(expected.toFixture())

        verify(fixtureDao).insert(expected)
    }

    @Test(expected = SQLiteException::class)
    fun `insert rethrows DAO exception`() {
        val expected = TestData.FIXTURE_ENTITY_1
        given { fixtureDao.insert(expected) }.willThrow(SQLiteException())

        roomFixtureDataSource.insert(expected.toFixture())
    }

    @Test
    fun `delete calls DAO successfully`() {
        val expected = TestData.FIXTURE_ENTITY_1

        roomFixtureDataSource.delete(expected.toFixture())

        verify(fixtureDao).delete(expected)
    }

    @Test(expected = SQLiteException::class)
    fun `delete rethrows DAO exception`() {
        val expected = TestData.FIXTURE_ENTITY_1
        given { fixtureDao.delete(expected) }.willThrow(SQLiteException())

        roomFixtureDataSource.delete(expected.toFixture())
    }
}