package com.kansus.teammaker.android.data.source

import com.kansus.teammaker.android.data.AppDatabase
import com.kansus.teammaker.android.data.entity.FixtureEntity
import com.kansus.teammaker.android.data.entity.PlayerEntity
import com.kansus.teammaker.android.data.entity.TeamEntity
import com.kansus.teammaker.android.data.entity.join.FixtureWithTeams
import com.kansus.teammaker.core.Either
import com.kansus.teammaker.core.exception.Failure
import com.kansus.teammaker.data.source.FixtureDataSource
import com.kansus.teammaker.domain.model.Fixture
import com.kansus.teammaker.domain.model.Player
import com.kansus.teammaker.domain.model.Team

class RoomFixtureDataSource(
    private val database: AppDatabase
) : FixtureDataSource {

    override fun get(fixtureId: Int): Either<Failure, Fixture> {
        return Either.Right(database.fixtureDao().getWithTeams(fixtureId).toFixture())
    }

    override fun getAll(gameId: Int): Either<Failure, List<Fixture>> {
        return Either.Right(database.fixtureDao().getAll(gameId).toFixtureList())
    }

    override fun insert(fixture: Fixture) {
        database.fixtureDao().insert(fixture.toFixtureEntity())
    }

    override fun delete(fixture: Fixture) {
        database.fixtureDao().delete(fixture.toFixtureEntity())
    }
}

fun Fixture.toFixtureEntity() = FixtureEntity(id, date, gameId)

fun FixtureEntity.toFixture() = Fixture(id, date, gameId, listOf())

fun FixtureWithTeams.toFixture() = with(fixture) {
    Fixture(id, date, gameId, teams.toTeamList())
}

fun TeamEntity.toTeam() = Team(id, date, fixtureId, listOf())

fun PlayerEntity.toPlayer() = Player(id, name)

fun List<PlayerEntity>.toPlayerList() = map { it.toPlayer() }

fun List<TeamEntity>.toTeamList() = map { it.toTeam() }

fun List<FixtureEntity>.toFixtureList() = map { it.toFixture() }