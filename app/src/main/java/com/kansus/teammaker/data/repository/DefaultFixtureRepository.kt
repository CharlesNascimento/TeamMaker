package com.kansus.teammaker.data.repository

import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.data.source.FixtureDataSource
import com.kansus.teammaker.domain.model.Fixture
import com.kansus.teammaker.domain.repository.FixtureRepository
import java.util.*
import javax.inject.Inject

class DefaultFixtureRepository @Inject constructor(
    private val fixtureDataSource: FixtureDataSource
) : FixtureRepository {

    override fun get(fixtureId: Int): Either<Failure, Fixture> {
        return Either.Right(Fixture(0, Date(), 1, listOf()))//gameDataSource.get(gameId)
    }

    override fun getWithTeams(fixtureId: Int): Either<Failure, Fixture> {
        return Either.Right(Fixture(0, Date(), 1, listOf()))
    }

    override fun getAll(): Either<Failure, List<Fixture>> {
        return Either.Right(listOf())//gameDataSource.getAll()
    }

    override fun insert(fixture: Fixture) {
        fixtureDataSource.insert(fixture)
    }

    override fun delete(fixture: Fixture) {
        fixtureDataSource.delete(fixture)
    }
}
