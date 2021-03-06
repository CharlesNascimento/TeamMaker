package com.kansus.teammaker.domain.repository

import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.domain.model.Fixture

interface FixtureRepository {

    fun get(fixtureId: Int): Either<Failure, Fixture>

    fun getAll(gameId: Int): Either<Failure, List<Fixture>>

    fun insert(fixture: Fixture)

    fun delete(fixture: Fixture)
}