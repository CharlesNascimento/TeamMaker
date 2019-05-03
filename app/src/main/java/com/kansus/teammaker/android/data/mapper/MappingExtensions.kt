package com.kansus.teammaker.android.data.mapper

import com.kansus.teammaker.android.data.entity.FixtureEntity
import com.kansus.teammaker.android.data.entity.GameEntity
import com.kansus.teammaker.android.data.entity.PlayerEntity
import com.kansus.teammaker.android.data.entity.TeamEntity
import com.kansus.teammaker.android.data.entity.join.FixtureWithTeams
import com.kansus.teammaker.domain.model.Fixture
import com.kansus.teammaker.domain.model.Game
import com.kansus.teammaker.domain.model.Player
import com.kansus.teammaker.domain.model.Team

fun Game.toGameEntity() = GameEntity(id, name, description)

fun GameEntity.toGame() = Game(id, name, description)

fun List<GameEntity>.toGameList() = map { it.toGame() }

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