package com.kansus.teammaker.data

import com.kansus.teammaker.android.data.entity.FixtureEntity
import com.kansus.teammaker.android.data.entity.GameEntity
import com.kansus.teammaker.android.data.entity.PlayerEntity
import com.kansus.teammaker.android.data.entity.TeamEntity
import java.util.*

/**
 * Utility class that holds values to be used for testing.
 */
object TestData {

    val GAME_ENTITY_1 = GameEntity(1, "Game 1", "Game 1 description")
    val GAME_ENTITY_2 = GameEntity(2, "Game 2", "Game 2 description")

    val GAMES = Arrays.asList(
        GAME_ENTITY_1,
        GAME_ENTITY_2
    )

    val PLAYER_ENTITY_1 = PlayerEntity(1, "Player 1")
    val PLAYER_ENTITY_2 = PlayerEntity(2, "Player 2")

    val PLAYERS = Arrays.asList(
        PLAYER_ENTITY_1,
        PLAYER_ENTITY_2
    )

    val FIXTURE_ENTITY_1 = FixtureEntity(1, Date(), GAME_ENTITY_1.id)

    val FIXTURE_ENTITY_2 = FixtureEntity(2, Date(), GAME_ENTITY_1.id)

    val TEAM_ENTITY_1 = TeamEntity(1, Date(), FIXTURE_ENTITY_1.id)

    val TEAM_ENTITY_2 = TeamEntity(2, Date(), FIXTURE_ENTITY_1.id)
}