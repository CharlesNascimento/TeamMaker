package com.kansus.teammaker.data.repository

import android.database.sqlite.SQLiteException
import com.kansus.teammaker.TestData.FIXTURES
import com.kansus.teammaker.TestData.FIXTURE_1
import com.kansus.teammaker.TestData.FIXTURE_2
import com.kansus.teammaker.TestData.GAME_1
import com.kansus.teammaker.UnitTest
import com.kansus.teammaker.core.Either.Left
import com.kansus.teammaker.core.Either.Right
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.kansus.teammaker.data.source.FixtureDataSource
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mock


class DefaultFixtureRepositoryTest : UnitTest() {

    private lateinit var fixtureRepository: DefaultFixtureRepository

    @Mock
    private lateinit var fixtureDataSource: FixtureDataSource

    @Before
    fun setUp() {
        fixtureRepository = DefaultFixtureRepository(fixtureDataSource)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `get returns right on success`() {
        given { fixtureDataSource.get(1) }.willReturn(Right(FIXTURE_1))

        val fixture = fixtureRepository.get(1)

        then(fixtureDataSource).should().get(1)
        fixture `should equal` (Right(FIXTURE_1))
    }

    @Test
    fun `get returns left on failure`() {
        given { fixtureDataSource.get(1) }.willReturn(Left(DatabaseError))

        val fixture = fixtureRepository.get(1)

        then(fixtureDataSource).should().get(1)
        fixture `should equal` (Left(DatabaseError))
    }

    @Test
    fun `getAll returns right on success`() {
        given { fixtureDataSource.getAll(GAME_1.id) }.willReturn(Right(FIXTURES))

        val fixtures = fixtureRepository.getAll(GAME_1.id)

        then(fixtureDataSource).should().getAll(GAME_1.id)
        fixtures `should equal` (Right(FIXTURES))
        fixtures.either({}) { fixtureList ->
            fixtureList `should contain` FIXTURE_1
            fixtureList `should contain` FIXTURE_2
        }
    }

    @Test
    fun `getAll returns left on failure`() {
        given { fixtureDataSource.getAll(GAME_1.id) }.willReturn(Left(DatabaseError))

        val fixtures = fixtureRepository.getAll(GAME_1.id)

        then(fixtureDataSource).should().getAll(GAME_1.id)
        fixtures `should equal` (Left(DatabaseError))
    }

    @Test
    fun `insert calls data source successfully`() {
        val expected = FIXTURE_1

        fixtureRepository.insert(expected)

        then(fixtureDataSource).should().insert(expected)
    }

    @Test(expected = SQLiteException::class)
    fun `insert rethrows data source exception`() {
        val expected = FIXTURE_1
        given { fixtureDataSource.insert(expected) }.willThrow(SQLiteException())

        fixtureRepository.insert(expected)
    }

    @Test
    fun `delete calls data source successfully`() {
        val expected = FIXTURE_1

        fixtureRepository.delete(expected)

        then(fixtureDataSource).should().delete(expected)
    }

    @Test(expected = SQLiteException::class)
    fun `delete rethrows data source exception`() {
        val expected = FIXTURE_1
        given { fixtureDataSource.delete(expected) }.willThrow(SQLiteException())

        fixtureRepository.delete(expected)
    }
}