package com.kansus.teammaker

import com.kansus.teammaker.android.data.entity.FixtureEntity
import com.kansus.teammaker.android.data.entity.GameEntity
import com.kansus.teammaker.android.data.entity.PlayerEntity
import com.kansus.teammaker.android.data.entity.TeamEntity
import com.kansus.teammaker.android.data.mapper.*
import com.kansus.teammaker.domain.model.Fixture
import com.kansus.teammaker.domain.model.Game
import java.util.*

/**
 * Utility class that holds values to be used for testing.
 */
object TestData {

    val GAME_ENTITY_1 = GameEntity(1, "Game 1", "Game 1 description")
    val GAME_ENTITY_2 = GameEntity(2, "Game 2", "Game 2 description")

    val GAME_1 = GAME_ENTITY_1.toGame()
    val GAME_2 = GAME_ENTITY_2.toGame()

    val GAME_ENTITIES: List<GameEntity> = Arrays.asList(
        GAME_ENTITY_1,
        GAME_ENTITY_2
    )

    val GAMES: List<Game> = GAME_ENTITIES.toGameList()

    val PLAYER_ENTITY_1 = PlayerEntity(1, "Player 1")
    val PLAYER_ENTITY_2 = PlayerEntity(2, "Player 2")

    val PLAYER_1 = PLAYER_ENTITY_1.toPlayer()
    val PLAYER_2 = PLAYER_ENTITY_2.toPlayer()

    val PLAYER_ENTITITES = Arrays.asList(
        PLAYER_ENTITY_1,
        PLAYER_ENTITY_2
    )

    var PLAYERS = PLAYER_ENTITITES.toPlayerList()

    val FIXTURE_ENTITY_1 = FixtureEntity(1, Date(), GAME_ENTITY_1.id)
    val FIXTURE_ENTITY_2 = FixtureEntity(2, Date(), GAME_ENTITY_1.id)

    var FIXTURE_1 = FIXTURE_ENTITY_1.toFixture()
    var FIXTURE_2 = FIXTURE_ENTITY_2.toFixture()

    val FIXTURE_ENTITIES: List<FixtureEntity> = Arrays.asList(
        FIXTURE_ENTITY_1,
        FIXTURE_ENTITY_2
    )

    val FIXTURES: List<Fixture> = FIXTURE_ENTITIES.toFixtureList()

    val TEAM_ENTITY_1 = TeamEntity(1, Date(), FIXTURE_ENTITY_1.id)
    val TEAM_ENTITY_2 = TeamEntity(2, Date(), FIXTURE_ENTITY_1.id)
}