package com.kansus.teammaker.data.entity.join

import androidx.room.Embedded
import androidx.room.Relation
import com.kansus.teammaker.data.entity.*

data class FixtureWithTeams(
    @Embedded
    val fixture: FixtureEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "fixtureId",
        entity = TeamEntity::class
    ) val teams: List<TeamEntity>
)