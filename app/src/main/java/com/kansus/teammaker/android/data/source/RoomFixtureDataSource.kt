package com.kansus.teammaker.android.data.source

import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.mapper.toFixture
import com.kansus.teammaker.android.data.mapper.toFixtureEntity
import com.kansus.teammaker.android.data.mapper.toFixtureList
import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.core.exception.Failure.DatabaseError
import com.kansus.teammaker.core.runEither
import com.kansus.teammaker.data.source.FixtureDataSource
import com.kansus.teammaker.domain.model.Fixture

class RoomFixtureDataSource(
    private val database: AppDatabase
) : FixtureDataSource {

    override fun get(fixtureId: Int): Either<Failure, Fixture> = runEither(DatabaseError) {
        database.fixtureDao().getWithTeams(fixtureId).toFixture()
    }

    override fun getAll(gameId: Int): Either<Failure, List<Fixture>> = runEither(DatabaseError) {
        database.fixtureDao().getAll(gameId).toFixtureList()
    }

    override fun insert(fixture: Fixture) {
        database.fixtureDao().insert(fixture.toFixtureEntity())
    }

    override fun delete(fixture: Fixture) {
        database.fixtureDao().delete(fixture.toFixtureEntity())
    }
}
