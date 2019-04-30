package com.kansus.teammaker.data

import TestData
import com.kansus.teammaker.UnitTest
import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.dao.FixtureDao
import com.kansus.teammaker.android.data.entity.join.FixtureWithTeams
import com.kansus.teammaker.android.data.source.RoomFixtureDataSource
import com.kansus.teammaker.android.data.source.toFixture
import com.kansus.teammaker.android.data.source.toFixtureList
import com.kansus.teammaker.core.Either.Right
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
    fun get() {
        val expected = FixtureWithTeams(TestData.FIXTURE_ENTITY_1, listOf())
        given { fixtureDao.getWithTeams(any()) }.willReturn(expected)

        val fixture = roomFixtureDataSource.get(1)

        verify(fixtureDao).getWithTeams(any())
        assert(fixture.isRight)
        fixture `should equal` Right(expected.toFixture())
    }

    @Test
    fun getAll() {
        val expected = TestData.FIXTURES
        given { fixtureDao.getAll(any()) }.willReturn(expected)

        val fixture = roomFixtureDataSource.getAll(1)

        verify(fixtureDao).getAll(any())
        assert(fixture.isRight)
        fixture `should equal` Right(expected.toFixtureList())
    }

    @Test
    fun insert() {
        val expected = TestData.FIXTURE_ENTITY_1

        roomFixtureDataSource.insert(expected.toFixture())

        verify(fixtureDao).insert(expected)
    }

    @Test
    fun delete() {
        val expected = TestData.FIXTURE_ENTITY_1

        roomFixtureDataSource.delete(expected.toFixture())

        verify(fixtureDao).delete(expected)
    }
}